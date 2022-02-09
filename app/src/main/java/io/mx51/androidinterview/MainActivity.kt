package io.mx51.androidinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.mx51.androidinterview.app_ui.ErrorScreen
import io.mx51.androidinterview.app_ui.LoadingScreen
import io.mx51.androidinterview.viewmodels.WeatherViewModel
import io.mx51.androidinterview.app_ui.WeatherDetailsScreen
import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.model.WeatherDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWeatherDetails(Units.IMPERIAL)

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when(uiState) {
                        is WeatherViewModel.State.Loading -> {
                            setContent {
                                LoadingScreen()
                            }
                        }
                        is WeatherViewModel.State.Loaded -> {
                            setWeatherDetailsScreen(uiState.weatherDetails)
                        }
                        is WeatherViewModel.State.LoadError -> {
                            setContent {
                                ErrorScreen(message = uiState.message) {
                                    if (uiState.unit == Units.IMPERIAL) {
                                        viewModel.getWeatherDetails(Units.IMPERIAL)
                                    } else {
                                        viewModel.getWeatherDetails(Units.METRIC)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setWeatherDetailsScreen(weatherDetails: WeatherDetails, isWindSpeedInMeterPerSecond: Boolean = true) {
        setContent {
            WeatherDetailsScreen(
                isImperialUnit = weatherDetails.units == Units.IMPERIAL,
                locationName = weatherDetails.locationName,
                isWindSpeedInMeterPerSecond = isWindSpeedInMeterPerSecond,
                temperature = weatherDetails.temperature,
                windSpeed = weatherDetails.windSpeed,
                description = weatherDetails.description,
                onRefreshClicked = {
                    if (weatherDetails.units == Units.IMPERIAL) {
                        viewModel.getWeatherDetails(Units.IMPERIAL)
                    } else {
                        viewModel.getWeatherDetails(Units.METRIC)
                    }
                },
                onSwitchTemperatureUnitClick = { isToImperialUnit ->
                    if (isToImperialUnit) {
                        viewModel.getWeatherDetails(Units.IMPERIAL)
                    }  else {
                        viewModel.getWeatherDetails(Units.METRIC)
                    }

                }
            ) { isWindSpeedToMeterPerSecond ->
                val copy = if (isWindSpeedToMeterPerSecond) {
                    weatherDetails.copy(
                        windSpeed = weatherDetails.windSpeed / 1.944
                    )
                } else {
                    weatherDetails.copy(
                        windSpeed = weatherDetails.windSpeed * 1.94384
                    )
                }
                setWeatherDetailsScreen(copy, isWindSpeedToMeterPerSecond)
            }
        }
    }
}