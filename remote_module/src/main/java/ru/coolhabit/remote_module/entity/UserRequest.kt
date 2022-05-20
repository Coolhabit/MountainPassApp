package ru.coolhabit.remote_module.entity

import com.google.gson.annotations.SerializedName

private const val ID = "id"
private const val EMAIL = "email"
private const val PHONE = "phone"
private const val FAM = "fam"
private const val NAME = "name"

data class UserRequest(
    @SerializedName(ID)
    val id: Int,
    @SerializedName(EMAIL)
    val email: String,
    @SerializedName(PHONE)
    val phone: String,
    @SerializedName(FAM)
    val fam: String,
    @SerializedName(NAME)
    val name: String
)