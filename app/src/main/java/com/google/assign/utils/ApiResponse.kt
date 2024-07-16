package com.google.assign.utils

import com.google.assign.R
import com.google.assign.di.App
import org.json.JSONObject
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(apiCall: Call<T>): ApiResponse<T> {
            val resources = App.resource

            try {
                val response = apiCall.execute()
                return if (response.isSuccessful) {
                    val body = response.body()

                    // Empty body
                    if (body == null || response.code() == 204) {
                        val headers = response.headers()
                        if (headers.size > 0) {
                            val headersMap: HashMap<String, Any> = hashMapOf()
                            headers.forEach {
                                headersMap[it.first] = it.second
                            }
                            return ApiSuccessEmptyResponseWithHeaders(headersMap)
                        }
                        return ApiSuccessEmptyResponse()
                    } else {
                        ApiSuccessResponse(body)
                    }

                } else {

                    if (response.code() == 401 || response.code() == 402) {
                        ApiErrorResponse(response.code().toString())
                    } else {
                        val msg = response.errorBody()?.string()
                        val errorMessage = if (msg.isNullOrEmpty()) {
                            response.message()
                        } else {
                            val errorObject = JSONObject(msg)
                            if (errorObject.has("message")) errorObject.getString("message") else null
                        }
                        ApiErrorResponse(
                            errorMessage ?: resources.getString(R.string.general_error)
                        )
                    }
                }
            } catch (throwable: Exception) {
                throwable.printStackTrace()
                return when (throwable) {
                    is IOException -> ApiErrorResponse(resources.getString(R.string.no_internet))
                    is HttpException -> {
                        val errorMessage = throwable.response()?.errorBody()?.string()
                        ApiErrorResponse(
                            errorMessage ?: resources.getString(R.string.general_error)
                        )
                    }
                    else -> {
                        ApiErrorResponse(resources.getString(R.string.general_error))
                    }
                }
            }
        }
    }

    class ApiSuccessResponse<T>(val data: T) : ApiResponse<T>()
    class ApiSuccessEmptyResponse<T> : ApiResponse<T>()
    class ApiSuccessEmptyResponseWithHeaders<T>(val headers: Map<String, Any>) : ApiResponse<T>()
    class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
}
