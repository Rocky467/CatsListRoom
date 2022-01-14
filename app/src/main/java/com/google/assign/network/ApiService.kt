package com.google.assign.network

import com.google.assign.model.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val USER = "random_user"
        const val CHAR = "characters"
    }

    @GET(USER)
    suspend fun getUsers(@Query("size") size: Int): Response<UserData>

}
