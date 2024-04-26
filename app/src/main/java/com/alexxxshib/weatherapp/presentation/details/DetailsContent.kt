package com.alexxxshib.weatherapp.presentation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexxxshib.weatherapp.R
import com.alexxxshib.weatherapp.domain.entity.Forecast
import com.alexxxshib.weatherapp.domain.entity.Weather
import com.alexxxshib.weatherapp.presentation.extensions.formattedFullDate
import com.alexxxshib.weatherapp.presentation.extensions.formattedShortDayOfWeek
import com.alexxxshib.weatherapp.presentation.extensions.tempToFormattedString
import com.alexxxshib.weatherapp.presentation.ui.theme.CardGradients
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun DetailsContent(component: DetailsComponent) {
    val state by component.model.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CardGradients.gradients[1].primaryGradient),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                cityName = state.city.name,
                isCityFavourite = state.isFavourite,
                onBackClick = { component.onClickBack() },
                onClickChangeFavouriteStatus = { component.onClickChangeFavouriteStatus() }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val forecastState = state.forecastState) {
                DetailsStore.State.ForecastState.Error -> {
                    Error()
                }

                DetailsStore.State.ForecastState.Initial -> {
                    Initial()
                }

                is DetailsStore.State.ForecastState.Loaded -> {
                    Forecast(forecast = forecastState.forecast)
                }

                DetailsStore.State.ForecastState.Loading -> {
                    Loading()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    cityName: String,
    isCityFavourite: Boolean,
    onBackClick: () -> Unit,
    onClickChangeFavouriteStatus: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = cityName) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        actions = {
            IconButton(onClick = { onClickChangeFavouriteStatus() }) {
                Icon(
                    imageVector = if (isCityFavourite) {
                        Icons.Default.Star
                    } else {
                        Icons.Default.StarBorder
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    )
}

@Composable
private fun Error() {

}

@Composable
private fun Initial() {

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Forecast(forecast: Forecast) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = forecast.currentWeather.conditionText,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = forecast.currentWeather.tempC.tempToFormattedString(),
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 70.sp)
            )
            GlideImage(
                modifier = Modifier.size(70.dp),
                model = forecast.currentWeather.conditionUrl,
                contentDescription = null
            )
        }
        Text(
            text = forecast.currentWeather.calendar.formattedFullDate(),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        AnimatedUpcomingWeather(
            upcoming = forecast.upcoming
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
private fun AnimatedUpcomingWeather(
    modifier: Modifier = Modifier,
    upcoming: List<Weather>
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(500)) +
                slideIn(
                    animationSpec = tween(500),
                    initialOffset = { IntOffset(0, it.height) }
                )
    ) {
        UpcomingWeather(
            modifier = modifier,
            upcoming = upcoming
        )
    }
}

@Composable
private fun UpcomingWeather(
    modifier: Modifier = Modifier,
    upcoming: List<Weather>
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = 0.24f
            )
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.upcoming),
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                upcoming.forEach {
                    ForecastDayCard(
                        modifier = Modifier
                            .weight(1f),
                        weather = it
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ForecastDayCard(
    modifier: Modifier = Modifier,
    weather: Weather
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.tempC.tempToFormattedString(),
                style = MaterialTheme.typography.titleMedium
            )
            GlideImage(
                modifier = Modifier.size(56.dp),
                model = weather.conditionUrl,
                contentDescription = null
            )
            Text(
                text = weather.calendar.formattedShortDayOfWeek(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.background
        )
    }
}