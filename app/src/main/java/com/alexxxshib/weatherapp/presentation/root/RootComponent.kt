package com.alexxxshib.weatherapp.presentation.root

import com.alexxxshib.weatherapp.presentation.details.DetailsComponent
import com.alexxxshib.weatherapp.presentation.favourite.FavouriteComponent
import com.alexxxshib.weatherapp.presentation.search.SearchComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Favourites(val component: FavouriteComponent) : Child

        data class Search(val component: SearchComponent) : Child

        data class Details(val component: DetailsComponent) : Child
    }
}