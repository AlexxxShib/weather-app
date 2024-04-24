package com.alexxxshib.weatherapp.presentation.favourite

import com.alexxxshib.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onClickSearch()

    fun onClickAddFavourite()

    fun onClickItemCity(city: City)
}