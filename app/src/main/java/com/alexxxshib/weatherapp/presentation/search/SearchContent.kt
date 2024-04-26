package com.alexxxshib.weatherapp.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexxxshib.weatherapp.R
import com.alexxxshib.weatherapp.domain.entity.City

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(component: SearchComponent) {
    val state by component.model.collectAsState()

    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    SearchBar(
        modifier = Modifier.focusRequester(focusRequester),
        query = state.searchQuery,
        placeholder = { Text(text = stringResource(R.string.search))},
        onQueryChange = { component.onClickChangeSearchQuery(it) },
        onSearch = { component.onClickSearch() },
        active = true,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = { component.onClickBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { component.onClickSearch() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    ) {
        when (val searchState = state.searchState) {
            SearchStore.State.SearchState.EmptyResult -> {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.no_city_found)
                )
            }

            SearchStore.State.SearchState.Error -> {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.something_went_wrong)
                )
            }

            SearchStore.State.SearchState.Initial -> {

            }

            SearchStore.State.SearchState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            is SearchStore.State.SearchState.SuccessLoaded -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        items = searchState.cities,
                        key = { it.id }
                    ) { city ->
                        CityCard(
                            modifier = Modifier
                                .clickable {
                                    component.onClickCity(city)
                                },
                            city = city
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CityCard(
    modifier: Modifier = Modifier,
    city: City
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.24f)
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = city.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = city.country,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}