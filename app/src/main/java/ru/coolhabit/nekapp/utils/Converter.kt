package ru.coolhabit.nekapp.utils

import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.remote_module.entity.*

object Converter {

    fun convertNektoNekRequest(nek: Nek): NekPostRequest {
        val list: List<ImageRequest> = emptyList()
        return NekPostRequest(
            beautyTitle = "пер.",
            title = nek.title,
            addTime = nek.date,
            otherTitles = "нет",
            connect = "",
            type = "pass",
            user = UserRequest(nek.userId, nek.userEmail, nek.userPhone, nek.userSurname, nek.userName),
            coords = CoordsRequest(nek.latitude, nek.longtitude, nek.height),
            level = LevelRequest(nek.level, nek.level, nek.level, nek.level),
            images = list
        )
    }
}