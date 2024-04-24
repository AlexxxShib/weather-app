package com.alexxxshib.weatherapp.presentation.root

import androidx.compose.runtime.Composable
import com.alexxxshib.weatherapp.presentation.details.DetailsContent
import com.alexxxshib.weatherapp.presentation.favourite.FavouriteContent
import com.alexxxshib.weatherapp.presentation.search.SearchContent
import com.alexxxshib.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children

@Composable
fun RootContent(rootComponent: RootComponent) {
    WeatherAppTheme {
        Children(
            stack = rootComponent.stack
        ) {

            when (val instance = it.instance) {
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instance.component)
                }

                is RootComponent.Child.Favourites -> {
                    FavouriteContent(component = instance.component)
                }

                is RootComponent.Child.Search -> {
                    SearchContent(component = instance.component)
                }
            }
        }
    }
}