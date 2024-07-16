package com.google.assign.di

import com.google.assign.network.CatsRemoteDataSource
import org.koin.dsl.module

object DataSourceModule {

    val dataSourceModule = module {
        single { CatsRemoteDataSource(get()) }
    }
}
