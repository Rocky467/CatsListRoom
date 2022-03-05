package com.google.assign.network

import com.google.assign.utils.Util.validateApi

class CatsRemoteDataSource(val apiService: ApiService) {

    fun getCatById(catId: String) = validateApi(apiService.getCatById(catId))

}