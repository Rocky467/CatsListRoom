package com.google.assign.di

import android.app.Application
import com.google.assign.db.AppDB
import com.google.assign.utils.API_KEY
import com.google.assign.utils.AUTH_HEADER
import com.google.assign.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { provideAppDataBase(get()) }

    single { provideAuthInterceptor() }

    single { provideGson() }

    single {
        provideRetrofitInstance(get(), get())
    }

    single {
        provideOkHttpClient()
    }

}

fun provideAppDataBase(application: Application): AppDB = AppDB.getInstance(application)!!

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun provideAuthInterceptor(): Interceptor {
    return Interceptor { chain ->
        val newRequest = chain.request().newBuilder().addHeader(AUTH_HEADER, API_KEY).build()
        chain.proceed(newRequest)
    }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(provideAuthInterceptor())
    .connectTimeout(45, TimeUnit.SECONDS)
    .writeTimeout(45, TimeUnit.SECONDS)
    .readTimeout(45, TimeUnit.SECONDS)
    .addNetworkInterceptor(loggingInterceptor)
    .build()

fun provideGson(): Gson = GsonBuilder().create()

fun provideRetrofitInstance(client: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}