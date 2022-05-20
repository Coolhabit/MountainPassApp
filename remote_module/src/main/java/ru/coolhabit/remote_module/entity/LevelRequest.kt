package ru.coolhabit.remote_module.entity

import com.google.gson.annotations.SerializedName

private const val WINTER = "winter"
private const val SUMMER = "summer"
private const val AUTUMN = "autumn"
private const val SPRING = "spring"

data class LevelRequest(
    @SerializedName(WINTER)
    val winter: String,
    @SerializedName(SUMMER)
    val summer: String,
    @SerializedName(AUTUMN)
    val autumn: String,
    @SerializedName(SPRING)
    val spring: String
)