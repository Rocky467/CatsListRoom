package com.google.assignment.network

import com.google.assignment.model.CatsData
import com.google.assignment.model.NetworkCat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val CATS = "v1/images/search"
        private const val CAT_BY_ID = "v1/images/{catId}"
    }

    @GET(CATS)
    suspend fun getCats(
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<CatsData>

    @GET(CAT_BY_ID)
    suspend fun getCatById(@Path("catId") catId: String): Response<NetworkCat>

}
