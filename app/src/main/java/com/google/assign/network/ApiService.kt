package com.google.assign.network

import com.google.assign.model.CatsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val CATS = "v1/images/search"
    }

    @GET(CATS)
    suspend fun getCats(
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<CatsData>

}
