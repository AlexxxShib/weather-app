package com.alexxxshib.weatherapp.data.repository

import com.alexxxshib.weatherapp.data.local.db.FavouriteCitiesDao
import com.alexxxshib.weatherapp.data.mapper.toDbModel
import com.alexxxshib.weatherapp.data.mapper.toEntities
import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteCitiesDao: FavouriteCitiesDao
) : FavouriteRepository {

    override val favouriteCities: Flow<List<City>> = favouriteCitiesDao.getFavouriteCities()
        .map { it.toEntities() }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean> =
        favouriteCitiesDao.observeIsFavourite(cityId)

    override suspend fun addToFavourites(city: City) =
        favouriteCitiesDao.addToFavourite(city.toDbModel())

    override suspend fun removeFromFavourites(cityId: Int) =
        favouriteCitiesDao.removeFromFavourite(cityId)
}