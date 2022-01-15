package com.google.assign.di

import com.google.assign.network.Repository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        return@single provideAppDataBase(get()).catsDao()
    }

    single { Repository(get(), get()) }

}