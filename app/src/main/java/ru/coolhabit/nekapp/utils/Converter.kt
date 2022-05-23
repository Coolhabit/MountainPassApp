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
            user = UserRequest(1, "pochta", "iphone", "nikonorov", "aleksandr"),
            coords = CoordsRequest("25.32", "32.32", "1800"),
            level = LevelRequest("1A", "", "", "1B"),
            images = list
        )
    }
}