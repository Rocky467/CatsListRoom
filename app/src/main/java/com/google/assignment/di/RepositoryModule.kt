package com.google.assignment.di

import com.google.assignment.di.ApplicationModule.provideAppDataBase
import com.google.assignment.network.Repository
import org.koin.dsl.module

object RepositoryModule {

    val repositoryModule = module {
        single { return@single provideAppDataBase(get()).catsDao() }
        single { Repository(get(), get()) }
    }

}