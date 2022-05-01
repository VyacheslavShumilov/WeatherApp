package com.hfad.lesson2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//В рамках MVVM создаем две сущности, хранящие данные - Город и Погода

//чтобы сделать Parceble и Serializeble объекты нужно класс City и класс Weather пометить аннотацией @Parcelize
//и теперь больше ничего не добавляя эти классы мы можем хранить в Boundle и передавать
@Parcelize
data class City(
    val city: String,
    val lat: Double,
    val lon: Double
) : Parcelable