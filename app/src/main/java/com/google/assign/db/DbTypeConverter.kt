package com.google.assign.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DbTypeConverter {

    @TypeConverter
    fun fromAddressString(value: String): Address {
        val address = object : TypeToken<Address>() {}.type
        return Gson().fromJson(value, address)
    }

    @TypeConverter
    fun fromAddress(address: Address): String {
        val gson = Gson()
        return gson.toJson(address)
    }


}