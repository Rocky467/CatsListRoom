package com.google.assign.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_key")
data class CatsKey(
    @PrimaryKey
    val catId: String,
    val prevKey: Int?,
    val nextKey: Int?
)