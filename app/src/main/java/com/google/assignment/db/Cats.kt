package com.google.assignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class Cats(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var url: String = "",
    var width: Int = 0,
    var height: Int = 0
)