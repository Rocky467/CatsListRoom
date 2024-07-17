package com.google.assignment.network

import com.google.assignment.utils.Util.validateApi

class CatsRemoteDataSource(private val apiService: ApiService) {

    fun getCatById(catId: String) = validateApi(apiService.getCatById(catId))

}