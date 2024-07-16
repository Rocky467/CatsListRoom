package com.google.assignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_key")
data class CatsKey(
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)