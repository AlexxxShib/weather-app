package com.alexxxshib.weatherapp.domain.usecase

import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(city: City) = repository.getForecast(city)
}