package com.google.assignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remotekey WHERE id = :id")
    suspend fun getKeys(id: String): RemoteKey

    @Query("DELETE FROM remotekey")
    suspend fun deleteAllKeys()

}