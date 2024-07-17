package com.google.assignment.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatsDao {

    //CatKeys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatKeys(catsKey: List<CatsKey>)

    @Query("SELECT * FROM cat_key WHERE id = :id")
    suspend fun getCatKeys(id: String): CatsKey

    @Query("DELETE FROM cat_key")
    fun deleteAllCatKeys()


    //Cats
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCats(cats: List<Cats>)

    @Query("SELECT * FROM cats")
    fun getCats(): PagingSource<Int, Cats>

    @Query("DELETE FROM cats")
    fun deleteAllCats()

}