package ru.coolhabit.remote_module

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.POST
import ru.coolhabit.remote_module.entity.NekPostRequest


interface NekApi {

    @POST("/submitData")
    fun sendNek(): Observable<NekPostRequest>
}