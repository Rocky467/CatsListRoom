package com.google.assign.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.assign.utils.Const.DB_NAME

@Database(entities = [Cats::class, CatsKey::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun catsDao(): CatsDao

    companion object {
        @Volatile
        private var sInstance: AppDB? = null

        fun getInstance(context: Context): AppDB? {
            if (sInstance == null) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDB::class.java, DB_NAME)
                sInstance = instance.build()
            }
            return sInstance
        }
    }


    fun clearDB() {
        this@AppDB.clearAllTables()
    }

}