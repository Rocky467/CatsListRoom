package com.google.assign.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.assign.db.AppDB
import com.google.assign.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val appModule = module {

    single { provideAppDataBase(get()) }

    single { provideGson() }

    single {
        provideRetrofitInstance(get(), get())
    }

    single {
        provideOkHttpClient()
    }

    single {
        provideRequestOptions()
    }

    single {
        provideGlideInstance(get(), get())
    }

}

fun provideAppDataBase(application: Application): AppDB = AppDB.getInstance(application)!!

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
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

fun provideRequestOptions(): RequestOptions =
    RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)

fun provideGlideInstance(
    application: Application,
    requestOptions: RequestOptions
): RequestManager = Glide.with(application).setDefaultRequestOptions(requestOptions)
