package com.alexxxshib.weatherapp.domain.repository

import com.alexxxshib.weatherapp.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String) : List<City>
}