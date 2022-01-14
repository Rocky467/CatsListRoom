package com.google.assign.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.assign.model.User
import com.google.assign.utils.DB_NAME

@Database(entities = [UserEntity::class, UserKey::class], version = 1)
@TypeConverters(DbTypeConverter::class)
abstract class AppDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var sInstance: AppDB? = null

        fun getInstance(context: Context): AppDB? {
            if (sInstance == null) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, AppDB::class.java, DB_NAME)
                sInstance = instance.build()
            }
            return sInstance
        }
    }


    fun clearDB() {
        this@AppDB.clearAllTables()
    }

}