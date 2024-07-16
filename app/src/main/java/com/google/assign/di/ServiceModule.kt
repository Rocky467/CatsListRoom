package com.google.assign.di

import com.google.assign.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

object ServiceModule {

    val serviceModule = module {
        single { provideService(get()) }
    }

    private fun provideService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}