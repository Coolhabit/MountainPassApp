package ru.coolhabit.remote_module.entity

import com.google.gson.annotations.SerializedName

private const val URL = "url"
private const val TITLE = "title"

data class ImageRequest(
    @SerializedName(URL)
    val url: String,
    @SerializedName(TITLE)
    val title: String
)
