package ru.coolhabit.remote_module

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.coolhabit.remote_module.entity.NekPostRequest


interface NekApi {
    @POST("v1/pereval/")
    fun sendNek(
        @Body nekPostRequest: NekPostRequest
    ): Completable
}