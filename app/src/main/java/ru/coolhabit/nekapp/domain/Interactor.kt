package ru.coolhabit.nekapp.domain

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.shared.PreferenceProvider
import ru.coolhabit.nekapp.utils.Converter
import ru.coolhabit.remote_module.NekApi


class Interactor(private val retrofitService: NekApi, private val preferences: PreferenceProvider) {

    fun sendNek(nek: Nek) = retrofitService.sendNek(Converter.convertNektoNekRequest(nek))
        .subscribeOn(Schedulers.io())
}
