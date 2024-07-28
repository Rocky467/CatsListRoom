package com.google.assignment.di

import android.app.Application
import com.google.assignment.db.AppDB
import com.google.assignment.db.CatsDao
import com.google.assignment.db.RemoteKeyDao
import org.koin.dsl.module

object DataBaseModule {

    val databaseModule = module {
        single { provideDataBase(get()) }
        single { provideCatDao(get()) }
        single { provideRemoteKeyDao(get()) }
    }

    private fun provideDataBase(application: Application): AppDB = AppDB.getDatabase(application)
    private fun provideCatDao(appDB: AppDB): CatsDao = appDB.catsDao()
    private fun provideRemoteKeyDao(appDB: AppDB): RemoteKeyDao = appDB.remoteKeyDao()

}