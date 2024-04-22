package com.alexxxshib.weatherapp.domain.repository

import com.alexxxshib.weatherapp.domain.entity.Forecast
import com.alexxxshib.weatherapp.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int) : Weather

    suspend fun getForecast(cityId: Int) : Forecast
}