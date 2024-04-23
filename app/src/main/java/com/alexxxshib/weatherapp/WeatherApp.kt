package com.alexxxshib.weatherapp

import android.app.Application
import com.alexxxshib.weatherapp.di.DaggerApplicationComponent

class WeatherApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}