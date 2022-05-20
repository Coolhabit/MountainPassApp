package ru.coolhabit.nekapp

import android.app.Application
import ru.coolhabit.nekapp.di.AppComponent
import ru.coolhabit.nekapp.di.DaggerAppComponent
import ru.coolhabit.nekapp.di.DatabaseModule
import ru.coolhabit.nekapp.di.DomainModule
import ru.coolhabit.remote_module.DaggerRemoteComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}