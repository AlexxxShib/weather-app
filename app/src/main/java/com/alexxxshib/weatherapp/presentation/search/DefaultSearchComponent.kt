package com.alexxxshib.weatherapp.presentation.search

import com.alexxxshib.weatherapp.domain.entity.City
import com.alexxxshib.weatherapp.presentation.extensions.componentScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSearchComponent @AssistedInject constructor(
    private val storeFactory: SearchStoreFactory,
    @Assisted("openReason") private val openReason: OpenReason,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("onForecastRequested") private val onForecastRequested: (City) -> Unit,
    @Assisted("onCitySavedToFavourite") private val onCitySavedToFavourite: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(openReason) }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    SearchStore.Label.ClickBack -> {
                        onBackClicked()
                    }

                    is SearchStore.Label.OpenForecast -> {
                        onForecastRequested(it.city)
                    }

                    SearchStore.Label.SaveToFavourite -> {
                        onCitySavedToFavourite()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State> = store.stateFlow

    override fun onClickChangeSearchQuery(query: String) {
        store.accept(SearchStore.Intent.ChangeSearchQuery(query))
    }

    override fun onClickBack() {
        store.accept(SearchStore.Intent.ClickBack)
    }

    override fun onClickSearch() {
        store.accept(SearchStore.Intent.ClickSearch)
    }

    override fun onClickCity(city: City) {
        store.accept(SearchStore.Intent.ClickCity(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("openReason") openReason: OpenReason,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onForecastRequested") onForecastRequested: (City) -> Unit,
            @Assisted("onCitySavedToFavourite") onCitySavedToFavourite: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultSearchComponent
    }
}