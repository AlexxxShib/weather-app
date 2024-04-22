package com.alexxxshib.weatherapp.data.mapper

import com.alexxxshib.weatherapp.data.network.dto.CityDto
import com.alexxxshib.weatherapp.domain.entity.City

fun CityDto.toEntity() = City(id, name, country)

fun List<CityDto>.toEntities() = map { it.toEntity() }