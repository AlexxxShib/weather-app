package com.alexxxshib.weatherapp.data.mapper

import com.alexxxshib.weatherapp.data.local.model.CityDbModel
import com.alexxxshib.weatherapp.domain.entity.City

fun City.toDbModel() = CityDbModel(id, name, country)

fun CityDbModel.toEntity() = City(id, name, country)

fun List<CityDbModel>.toEntities() = map { it.toEntity() }