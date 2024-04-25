package com.alexxxshib.weatherapp.presentation.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexxxshib.weatherapp.R
import com.alexxxshib.weatherapp.presentation.extensions.tempToFormattedString
import com.alexxxshib.weatherapp.presentation.ui.theme.CardGradients
import com.alexxxshib.weatherapp.presentation.ui.theme.Orange
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun FavouriteContent(component: FavouriteComponent) {

    val state by component.model.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            SearchCard(
                onClick = {
                    component.onClickSearch()
                }
            )
        }
        itemsIndexed(
            items = state.cityItems,
            key = { _, item -> item.city.id },
        ) { index, item ->
            CityCard(
                index = index,
                cityItem = item,
                onClick = {
                    component.onClickItemCity(item.city)
                }
            )
        }
        item {
            AddFavouriteCityCard(
                onClick = {
                    component.onClickAddFavourite()
                }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CityCard(
    index: Int,
    cityItem: FavouriteStore.State.CityItem,
    onClick: () -> Unit
) {
    val gradient = CardGradients.getGradientByIndex(index)

    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                spotColor = gradient.shadowColor,
                shape = MaterialTheme.shapes.extraLarge
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Blue
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier
                .background(gradient.primaryGradient)
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradient.secondaryGradient,
                        center = Offset(
                            x = center.x - size.width / 10,
                            y = center.y + size.height / 2
                        ),
                        radius = size.maxDimension / 2
                    )
                }
                .clickable { onClick() }
                .padding(24.dp)
        ) {
            when (val weatherState = cityItem.weatherState) {
                FavouriteStore.State.WeatherState.Error -> {}
                FavouriteStore.State.WeatherState.Initial -> {}

                is FavouriteStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        model = weatherState.iconUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 24.dp),
                        text = weatherState.tempC.tempToFormattedString(),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp),
                        color = MaterialTheme.colorScheme.background
                    )
                }

                FavouriteStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = cityItem.city.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
private fun AddFavouriteCityCard(
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp)
                .padding(24.dp)
                .clickable { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .size(48.dp),
                imageVector = Icons.Default.Edit,
                tint = Orange,
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.button_add_favourite),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun SearchCard(
    onClick: () -> Unit
) {
    val gradient = CardGradients.gradients[3]
    Card(
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient.primaryGradient)
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(R.string.search),
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

private fun CardGradients.getGradientByIndex(index: Int) =
    gradients[index % gradients.size]