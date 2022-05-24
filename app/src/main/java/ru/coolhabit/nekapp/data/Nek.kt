package ru.coolhabit.nekapp.data

import ru.coolhabit.remote_module.entity.ImageRequest

data class Nek(
    var title: String,
    var level: String,
    var date: String,

    var latitude: String,
    var longtitude: String,
    var height: String,

    var userId: Int,
    var userEmail: String,
    var userSurname: String,
    var userName: String,
    var userPhone: String,

    var imageList: List<ImageRequest>
)
