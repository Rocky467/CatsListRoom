package com.google.assign.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.assign.model.User

@Dao
interface UserDao {

    //UserKeys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(userKey: UserKey)

    @Query("SELECT * FROM UserKey ORDER BY id DESC")
    suspend fun getKeys(): List<UserKey>

    @Query("DELETE FROM UserKey")
    suspend fun deleteKey()


    //User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserEntity>): LongArray

    @Query("SELECT * FROM user")
    fun getUser(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user")
    suspend fun deleteUser()

}