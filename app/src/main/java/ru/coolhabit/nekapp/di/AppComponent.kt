package ru.coolhabit.nekapp.di

import dagger.Component
import ru.coolhabit.remote_module.RemoteProvider
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    dependencies = [RemoteProvider::class],
    modules = [
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
//    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
//    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
//    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
//    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}