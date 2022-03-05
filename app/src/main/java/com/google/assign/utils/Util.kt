package com.google.assign.utils

import android.util.Log
import retrofit2.Call


object Util {

    fun log(tag: String, msg: Any) {
        Log.d(tag, "$msg")
    }

    fun <T> validateApi(apiCall: Call<T>): Resource<T> {
        return when (val res = ApiResponse.create(apiCall)) {
            is ApiResponse.ApiSuccessResponse -> Resource.success(res.data)
            is ApiResponse.ApiSuccessEmptyResponse -> Resource.success(null)
            is ApiResponse.ApiErrorResponse -> Resource.error(res.errorMessage, null)
            is ApiResponse.ApiSuccessEmptyResponseWithHeaders -> Resource.success(null, res.headers)
        }
    }

}

