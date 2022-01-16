package com.google.assign.network

import com.google.assign.model.NetworkCat
import com.google.assign.utils.*
import retrofit2.Call

class CatsRemoteDataSource(val apiService: ApiService) {


    fun getCatById(catId: String): Resource<NetworkCat> {
        val apiCall = apiService.getCatById(catId)
        return validateCall(apiCall)
    }


    private fun <T> validateCall(apiCall: Call<T>): Resource<T> {
        return when (val res = ApiResponse.create(apiCall)) {
            is ApiSuccessResponse -> Resource.success(res.data)
            is ApiSuccessEmptyResponse -> Resource.success(null)
            is ApiErrorResponse -> Resource.error(res.errorMessage, null)
            is ApiSuccessEmptyResponseWithHeaders -> Resource.success(null, res.headers)
        }
    }


}