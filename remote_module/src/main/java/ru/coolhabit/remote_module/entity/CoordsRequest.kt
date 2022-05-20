package ru.coolhabit.remote_module.entity

import com.google.gson.annotations.SerializedName

private const val LATITUDE = "latitude"
private const val LONGTITUDE = "longitude"
private const val HEIGHT = "height"

data class CoordsRequest(
    @SerializedName(LATITUDE)
    val latitude: String,
    @SerializedName(LONGTITUDE)
    val longitude: String,
    @SerializedName(HEIGHT)
    val height: String
)