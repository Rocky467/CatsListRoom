package com.google.assign.di

import com.google.assign.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val dataSourceModule = module {

    single { get<Retrofit>().create(ApiService::class.java) }

}