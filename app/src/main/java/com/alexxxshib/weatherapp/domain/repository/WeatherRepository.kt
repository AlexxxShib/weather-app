package com.alexxxshib.weatherapp.domain.repository

import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.domain.entity.Forecast
import com.alexxxshib.weatherapp.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(city: City) : Weather

    suspend fun getForecast(city: City) : Forecast
}