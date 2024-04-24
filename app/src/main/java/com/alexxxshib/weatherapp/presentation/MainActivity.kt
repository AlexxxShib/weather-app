package com.alexxxshib.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexxxshib.weatherapp.WeatherApp
import com.alexxxshib.weatherapp.presentation.root.DefaultRootComponent
import com.alexxxshib.weatherapp.presentation.root.RootContent
import com.arkivanov.decompose.defaultComponentContext
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).component.inject(this)

        super.onCreate(savedInstanceState)
        val componentContext = defaultComponentContext()
        setContent {
            RootContent(rootComponent = rootComponentFactory.create(componentContext))
        }
    }
}
