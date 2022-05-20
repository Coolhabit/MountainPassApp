package ru.coolhabit.remote_module.entity

import com.google.gson.annotations.SerializedName

private const val BEAUTY_TITLE = "beautyTitle"
private const val TITLE = "title"
private const val OTHER_TITLES = "other_titles"
private const val CONNECT = "connect"
private const val ADD_TIME = "add_time"
private const val USER = "user"
private const val COORDS = "coords"
private const val TYPE = "type"
private const val LEVEL = "level"
private const val IMAGES = "images"

data class NekPostResponse (
    @SerializedName(BEAUTY_TITLE)
    val beautyTitle: String,
    @SerializedName(TITLE)
    val title: String,
    @SerializedName(OTHER_TITLES)
    val otherTitles: String,
    @SerializedName(CONNECT)
    val connect: String,
    @SerializedName(ADD_TIME)
    val addTime: String,
    @SerializedName(USER)
    val user: UserRequest,
    @SerializedName(COORDS)
    val coords: CoordsRequest,
    @SerializedName(TYPE)
    val type: String,
    @SerializedName(LEVEL)
    val level: LevelRequest,
    @SerializedName(IMAGES)
    val images: List<ImageRequest>
)