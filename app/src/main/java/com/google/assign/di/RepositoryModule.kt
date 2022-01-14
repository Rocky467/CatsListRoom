package com.google.assign.di

import com.google.assign.network.Repository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        return@single provideAppDataBase(get()).userDao()
    }

    single { Repository(get(), get()) }

}