package com.hfad.lesson2.data

//создаем три состояния приложения
sealed class AppState {
    data class Success(val weatherData: List<Weather>): AppState()
    data class Error(val error: Throwable): AppState()

    //т.к. Loading данные не передаются его можно сделать object
    object Loading: AppState()
}