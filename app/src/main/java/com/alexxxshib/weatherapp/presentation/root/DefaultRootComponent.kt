package com.alexxxshib.weatherapp.presentation.root

import android.os.Parcelable
import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.presentation.details.DefaultDetailsComponent
import com.alexxxshib.weatherapp.presentation.favourite.DefaultFavouriteComponent
import com.alexxxshib.weatherapp.presentation.search.DefaultSearchComponent
import com.alexxxshib.weatherapp.presentation.search.OpenReason
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favourite,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is Config.Details -> {
            val component = detailsComponentFactory.create(
                city = config.city,
                onBackClicked = {
                    navigation.pop()
                },
                componentContext = componentContext
            )
            RootComponent.Child.Details(component)
        }

        Config.Favourite -> {
            val component = favouriteComponentFactory.create(
                onCityItemClicked = {
                    navigation.push(Config.Details(it))
                },
                onSearchClicked = {
                    navigation.push(Config.Search(openReason = OpenReason.RegularSearch))
                },
                onAddFavouriteClicked = {
                    navigation.push(Config.Search(openReason = OpenReason.AddToFavourite))
                },
                componentContext = componentContext
            )
            RootComponent.Child.Favourites(component)
        }

        is Config.Search -> {
            val component = searchComponentFactory.create(
                openReason = config.openReason,
                onBackClicked = {
                    navigation.pop()
                },
                onForecastRequested = {
                    navigation.push(Config.Details(it))
                },
                onCitySavedToFavourite = {
                    navigation.pop()
                },
                componentContext = componentContext
            )
            RootComponent.Child.Search(component)
        }
    }


    private sealed interface Config : Parcelable {

        @Parcelize
        data object Favourite : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config

        @Parcelize
        data class Details(val city: City) : Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}