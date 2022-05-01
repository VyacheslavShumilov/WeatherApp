package com.hfad.lesson2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.lesson2.data.IReposotory
import com.hfad.lesson2.data.Repository
import com.hfad.lesson2.data.AppState
import java.lang.Thread.sleep


class MainViewModel(
    //создаю приватную LiveData, которая может меняться, у нее есть метод postValue (см. ниже)
    //этот метод отправляет данные в LiveData. ЭТО правильный способ!
    private val mutableLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: IReposotory = Repository()
) :
    ViewModel() {
    //ЭТО правильный способ!
    //обычную liveData делаю публичной (по умолчанию).
    //Далее get() который каждый раз при обращении будет обращаться к mutableLiveData
    //т.к. mutableLiveData наследник liveData, то можем приравнять их
    //на val liveData мы подписываемся в fun onViewCreated() в MainFragment
    val liveData: LiveData<AppState> get() = mutableLiveData

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        mutableLiveData.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            mutableLiveData.postValue(AppState.Success(
                if (isRussian) repository.getWeatherFromLocalStorageRus()
                else repository.getWeatherFromLocalStorageWorld()))
        }.start()
    }

}





//БЫЛО ДО разработки непосредственно Приложения
////к ViewModel прикручиваем sealed class <AppState>
//class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {
//    fun getLiveData(): MutableLiveData<AppState> {       //0_теперь на liveData можно подписаться
//
//        return liveDataToObserve
//    }
//
//    //5 Имитация загрузки данных из базы данных
//    private fun getDataFromLocalSource(){
//        Thread {
//            sleep(5000)
//            liveDataToObserve.postValue(AppState.Success(Any()))      //передача значения в liveData
//        }.start()
//    }
//
//    fun load() {
//        liveDataToObserve.postValue(AppState.Loading)    //в liveDataToObserve отправаляется AppState.Loading
//        getDataFromLocalSource()                         //и идет запрос данных
//
//    }
//
//}