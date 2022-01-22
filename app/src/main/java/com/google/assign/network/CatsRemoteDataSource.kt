package com.google.assign.network

import com.google.assign.model.NetworkCat
import com.google.assign.utils.*
import retrofit2.Call

class CatsRemoteDataSource(val apiService: ApiService) {

    fun getCatById(catId: String): Resource<NetworkCat> {
        val apiCall = apiService.getCatById(catId)
        return validateApi(apiCall)
    }

}