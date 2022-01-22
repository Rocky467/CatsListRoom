package com.google.assign.network

import com.google.assign.db.CatsData
import com.google.assign.model.NetworkCat
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val CATS = "v1/images/search"
        const val CAT_BY_ID = "v1/images/{catId}"
    }

    @GET(CATS)
    suspend fun getCats(
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<CatsData>

    @GET(CAT_BY_ID)
    fun getCatById(@Path("catId") catId: String): Call<NetworkCat>

}
