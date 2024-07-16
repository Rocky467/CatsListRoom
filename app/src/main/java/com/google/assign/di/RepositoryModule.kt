package com.google.assign.di

import com.google.assign.di.ApplicationModule.provideAppDataBase
import com.google.assign.network.Repository
import org.koin.dsl.module

object RepositoryModule {

    val repositoryModule = module {
        single { return@single provideAppDataBase(get()).catsDao() }
        single { Repository(get(), get()) }
    }

}