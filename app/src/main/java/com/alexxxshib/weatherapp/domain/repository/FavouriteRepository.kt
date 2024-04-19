package com.alexxxshib.weatherapp.domain.repository

import com.alexxxshib.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteCities: Flow<City>

    fun observeIsFavourite(cityId: Int) : Flow<Boolean>

    suspend fun addToFavourites(city: City)

    suspend fun removeFromFavourites(cityId: Int)
}