package com.alexxxshib.weatherapp.domain.usecase

import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    suspend fun addToFavourites(city: City) = repository.addToFavourites(city)

    suspend fun removeFromFavourites(cityId: Int) = repository.removeFromFavourites(cityId)
}