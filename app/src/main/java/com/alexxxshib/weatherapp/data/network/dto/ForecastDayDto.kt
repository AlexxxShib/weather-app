package com.alexxxshib.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDayDto(
    @SerializedName("date_epoch") val date: Long,
    @SerializedName("day") val dayWeather: DayWeatherDto
)
