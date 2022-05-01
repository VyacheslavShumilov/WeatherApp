package com.hfad.lesson2.data

import com.google.gson.annotations.SerializedName

data class FactDTO(
    val temp: Int?,
    //в GSon поле имеет название feels_like, но в Котлине такое написание не рекомендуется
    //поэтому создается аннотация к полю с написанием в Gson, а в коде пишем feelsLike
    //если тоакой аннотации не будет на экране отобразится null
    @SerializedName("feels_like")
    val feelsLike: Int?,
    val condition: String?
)
