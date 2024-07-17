package com.google.assignment.di

import com.google.assignment.utils.Const.API_KEY
import com.google.assignment.utils.Const.AUTH_HEADER
import com.google.assignment.utils.Const.BASE_URL
import com.google.assignment.utils.Const.TIME_OUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    val networkModule = module {
        single { provideAuthInterceptor() }
        single { provideRetrofit(get(), get()) }
        single { provideConverterFactory() }
        single { provideOkHttpClient() }
    }

    private fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader(AUTH_HEADER, API_KEY)
                .build()
        )
    }

    private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    private fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory
        .create()

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideAuthInterceptor())
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}

