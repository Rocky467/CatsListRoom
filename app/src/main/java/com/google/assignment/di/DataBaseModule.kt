package com.google.assignment.di

import android.app.Application
import com.google.assignment.db.AppDB
import com.google.assignment.db.CatsDao
import org.koin.dsl.module

object DataBaseModule {

    val databaseModule = module {
        single { provideDataBase(get()) }
        single { provideDao(get()) }
    }

    private fun provideDataBase(application: Application): AppDB = AppDB.getDatabase(application)

    private fun provideDao(appDB: AppDB): CatsDao = appDB.catsDao()

}