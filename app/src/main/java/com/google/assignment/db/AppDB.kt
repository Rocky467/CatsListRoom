package com.google.assignment.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cats::class, CatsKey::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun catsDao(): CatsDao

    fun clearDB() {
        this@AppDB.clearAllTables()
    }

}