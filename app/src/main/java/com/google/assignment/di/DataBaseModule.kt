package com.google.assignment.di

import android.app.Application
import com.google.assignment.db.AppDB
import org.koin.dsl.module

object DataBaseModule {

    val databaseModule = module {
        single { provideDataBase(get()) }
    }

    private fun provideDataBase(application: Application): AppDB = AppDB.getDatabase(application)

}