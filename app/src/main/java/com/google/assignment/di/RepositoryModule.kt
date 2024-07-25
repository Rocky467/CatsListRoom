package com.google.assignment.di

import com.google.assignment.network.Repository
import org.koin.dsl.module

object RepositoryModule {

    val repositoryModule = module {
        single { Repository(get(), get()) }
    }

}