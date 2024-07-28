package com.google.assignment.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cats>)

    @Query("SELECT * FROM cats")
    fun getCats(): PagingSource<Int, Cats>

    @Query("DELETE FROM cats")
    suspend fun deleteAllCats()

}