package ru.coolhabit.nekapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.coolhabit.nekapp.domain.Interactor
import ru.coolhabit.nekapp.shared.PreferenceProvider
import ru.coolhabit.remote_module.NekApi
import javax.inject.Singleton

@Module
//Передаем контекст для SharedPreferences через конструктор
class DomainModule(val context: Context) {
    //Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    //Создаем экземпляр SharedPreferences
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(tmdbApi: NekApi, preferenceProvider: PreferenceProvider) = Interactor(retrofitService = tmdbApi, preferences = preferenceProvider)
}