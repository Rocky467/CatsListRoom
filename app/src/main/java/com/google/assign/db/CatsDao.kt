package com.google.assign.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.assign.model.Cats

@Dao
interface CatsDao {

    //CatKeys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatKeys(catsKey: List<CatsKey>)

    @Query("SELECT * FROM cat_key WHERE id = :id")
    suspend fun getCatKeys(id: String): CatsKey?

    @Query("DELETE FROM cat_key")
    suspend fun deleteAllCatKeys()


    //Cats
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cats>)

    @Query("SELECT * FROM cats")
    fun getCats(): PagingSource<Int, Cats>

    @Query("DELETE FROM cats")
    suspend fun deleteAllCats()

}