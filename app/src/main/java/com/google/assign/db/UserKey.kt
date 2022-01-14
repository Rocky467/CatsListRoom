package com.google.assign.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserKey")
data class UserKey(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)