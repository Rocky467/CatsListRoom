package com.google.assign.model


import com.google.gson.annotations.SerializedName

data class NetworkCat(
    @SerializedName("height")
    var height: Int = 0,
    @SerializedName("id")
    var id: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("width")
    var width: Int = 0
)