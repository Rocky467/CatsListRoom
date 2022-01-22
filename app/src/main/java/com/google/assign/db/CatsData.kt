package com.google.assign.db

import androidx.room.Entity
import androidx.room.PrimaryKey

class CatsData :ArrayList<Cats>()

@Entity(tableName = "cats")
data class Cats(
    @PrimaryKey
    var id: String = "",
    var url: String = "",
    var width: Int = 0,
    var height: Int = 0
)