package com.google.assignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.assignment.utils.Const.DB_NAME

@Database(entities = [Cats::class, RemoteKey::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun catsDao(): CatsDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {

        @Volatile
        private var dbInstance: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            if (dbInstance == null) {
                synchronized(this) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDB::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return dbInstance!!
        }
    }

    fun clearDB() = this.clearAllTables()

}