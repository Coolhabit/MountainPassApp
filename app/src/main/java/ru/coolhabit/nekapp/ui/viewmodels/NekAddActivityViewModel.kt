package ru.coolhabit.nekapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.coolhabit.nekapp.App
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.domain.Interactor
import javax.inject.Inject

class NekAddActivityViewModel: ViewModel() {
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    var nekData = MutableLiveData<Nek>()

    init {
        App.instance.dagger.inject(this)
    }

    fun sendNek(nek: Nek) {
        interactor.sendNek(nek)
            .subscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onNext = {
                    nekData.postValue(nek)
                    println(nekData)
                }
            )
    }
}