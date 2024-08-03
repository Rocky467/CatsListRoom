package com.google.assignment.base

import com.google.assignment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiCall()
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error(response.errorBody()!!.string())
                }
            } catch (e: HttpException) {
                Resource.Error(e.message ?: "Http, Something went wrong")
            } catch (e: IOException) {
                Resource.Error(e.message ?: "IO, No internet connection")
            } catch (e: Exception) {
                Resource.Error(e.message ?: "Exception, Something went wrong")
            }
        }
    }

}