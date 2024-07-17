package com.google.assignment.di

import android.app.Application
import androidx.room.Room
import com.google.assignment.db.AppDB
import com.google.assignment.db.CatsDao
import com.google.assignment.utils.Const.DB_NAME
import org.koin.dsl.module

object DataBaseModule {

    val databaseModule = module {
        single { provideDataBase(get()) }
        single { provideDao(get()) }
    }

    private fun provideDataBase(application: Application): AppDB = Room.databaseBuilder(
        application,
        AppDB::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration().build()

    private fun provideDao(appDB: AppDB): CatsDao = appDB.catsDao()

}