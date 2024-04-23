package com.alexxxshib.weatherapp.di

import android.content.Context
import com.alexxxshib.weatherapp.data.local.db.FavouriteCitiesDao
import com.alexxxshib.weatherapp.data.local.db.FavouriteDatabase
import com.alexxxshib.weatherapp.data.network.api.ApiFactory
import com.alexxxshib.weatherapp.data.network.api.ApiService
import com.alexxxshib.weatherapp.data.repository.FavouriteRepositoryImpl
import com.alexxxshib.weatherapp.data.repository.SearchRepositoryImpl
import com.alexxxshib.weatherapp.data.repository.WeatherRepositoryImpl
import com.alexxxshib.weatherapp.domain.repository.FavouriteRepository
import com.alexxxshib.weatherapp.domain.repository.SearchRepository
import com.alexxxshib.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {

        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavouriteDatabase(context: Context): FavouriteDatabase =
            FavouriteDatabase.getInstance(context)

        @[ApplicationScope Provides]
        fun provideFavouriteCitiesDao(favouriteDatabase: FavouriteDatabase): FavouriteCitiesDao =
            favouriteDatabase.favouriteCitiesDao()
    }
}