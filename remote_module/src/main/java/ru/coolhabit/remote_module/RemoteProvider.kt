package ru.coolhabit.remote_module

interface RemoteProvider {
    fun provideRemote(): NekApi
}