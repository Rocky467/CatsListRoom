package com.google.assign.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var address: Address = Address(),
    var avatar: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = ""
)

data class Address(
    var city: String = "",
)