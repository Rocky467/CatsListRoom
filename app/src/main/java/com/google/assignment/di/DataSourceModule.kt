package com.google.assignment.di

import com.google.assignment.network.CatsRemoteDataSource
import org.koin.dsl.module

object DataSourceModule {

    val dataSourceModule = module {
        single { CatsRemoteDataSource(get()) }
    }
}
