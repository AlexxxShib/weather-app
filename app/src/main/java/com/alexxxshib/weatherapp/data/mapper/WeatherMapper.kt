package com.alexxxshib.weatherapp.data.mapper

import com.alexxxshib.weatherapp.data.network.dto.WeatherCurrentDto
import com.alexxxshib.weatherapp.data.network.dto.WeatherDto
import com.alexxxshib.weatherapp.data.network.dto.WeatherForecastDto
import com.alexxxshib.weatherapp.domain.entity.Forecast
import com.alexxxshib.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity() = weather.toEntity()

fun WeatherDto.toEntity() = Weather(
    tempC = tempC,
    conditionText = condition.text,
    conditionUrl = condition.iconUrl.correctImageUrl(),
    calendar = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = weather.toEntity(),
    upcoming = forecast.forecast.drop(1).map { forecastDayDto ->
        val weatherDto = forecastDayDto.dayWeather
        Weather(
            tempC = weatherDto.tempC,
            conditionText = weatherDto.condition.text,
            conditionUrl = weatherDto.condition.iconUrl.correctImageUrl(),
            calendar = forecastDayDto.date.toCalendar()
        )
    }
)

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)