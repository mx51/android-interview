package io.mx51.androidinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import io.mx51.androidinterview.app_ui.LoadingScreen
import io.mx51.androidinterview.viewmodels.WeatherViewModel
import io.mx51.androidinterview.app_ui.WeatherDetailsScreen
import io.mx51.androidinterview.data.model.MeasurementSystem
import io.mx51.androidinterview.data.model.TemperatureUnit
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWeatherDetails()

        setContent {
            val weatherDetails = viewModel.weatherDetails.collectAsState().value
            if (weatherDetails != null) {
                WeatherDetailsScreen(
                    locationName = weatherDetails.locationName,
                    temperature = weatherDetails.temperature.value,
                    windSpeed = weatherDetails.windSpeed.value,
                    description = weatherDetails.description,
                    useImperial = weatherDetails.temperature.unit == TemperatureUnit.Fahrenheit,
                    onUnitChange = { useImperialUnits ->
                        viewModel.convertLatestWeatherDataTo(
                            if(useImperialUnits) {
                                MeasurementSystem.Imperial
                            } else {
                                MeasurementSystem.Metric
                            }
                        )
                    }
                ) { useImperialUnits ->
                    viewModel.getWeatherDetails(
                        if(useImperialUnits) {
                            MeasurementSystem.Imperial
                        } else {
                            MeasurementSystem.Metric
                        }
                    )
                }
            } else {
                LoadingScreen()
            }
        }
    }
}