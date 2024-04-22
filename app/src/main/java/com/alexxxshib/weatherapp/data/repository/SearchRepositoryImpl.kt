package com.alexxxshib.weatherapp.data.repository

import com.alexxxshib.weatherapp.data.mapper.toEntities
import com.alexxxshib.weatherapp.data.network.api.ApiService
import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    override suspend fun search(query: String): List<City> =
        apiService.searchCity(query).toEntities()
}