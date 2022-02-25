package io.mx51.androidinterview.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mx51.androidinterview.data.extensions.convertTo
import io.mx51.androidinterview.data.extensions.defaultSpeedUnit
import io.mx51.androidinterview.data.extensions.temperatureUnit
import io.mx51.androidinterview.data.model.MeasurementSystem
import io.mx51.androidinterview.data.model.WeatherDetails
import io.mx51.androidinterview.domain.GetWeatherDetailsUseCase
import io.mx51.androidinterview.domain.WeatherRequestParameters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherDetailsUseCase: GetWeatherDetailsUseCase
): ViewModel() {

    private val _weatherDetails: MutableStateFlow<WeatherDetails?> = MutableStateFlow(null)

    val weatherDetails = _weatherDetails.asStateFlow()

    fun getWeatherDetails(
        system: MeasurementSystem = MeasurementSystem.Metric,
        location: String = "New York",
    ) {
        viewModelScope.launch {
            _weatherDetails.value = getWeatherDetailsUseCase {
                WeatherRequestParameters(
                    location = location,
                    units = system
                )
            }
        }
    }

    /**
     * @param system
     */
    fun convertLatestWeatherDataTo(
        system: MeasurementSystem
    ) {
        _weatherDetails.value?.let {
            _weatherDetails.value = WeatherDetails(
                temperature = it.temperature.convertTo(system.temperatureUnit()),
                windSpeed = it.windSpeed.convertTo(system.defaultSpeedUnit()),
                description = it.description,
                locationName = it.locationName
            )
        }
    }

    fun convertLatestWeatherDataAndRefresh(
        system: MeasurementSystem
    ) {
        convertLatestWeatherDataTo(
            system = system
        )

        getWeatherDetails(
            system = system
        )
    }
}