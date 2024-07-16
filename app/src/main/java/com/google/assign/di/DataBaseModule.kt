package com.google.assign.di

import android.app.Application
import androidx.room.Room
import com.google.assign.db.AppDB
import com.google.assign.db.CatsDao
import com.google.assign.utils.Const.DB_NAME
import org.koin.dsl.module

object DataBaseModule {

    val databaseModule = module {
        single { provideDataBase(get()) }
        single { provideDao(get()) }
    }

    private fun provideDataBase(application: Application): AppDB =
        Room.databaseBuilder(
            application,
            AppDB::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()

    private fun provideDao(appDB: AppDB): CatsDao = appDB.catsDao()
}

