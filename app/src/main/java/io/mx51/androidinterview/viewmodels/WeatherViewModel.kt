package io.mx51.androidinterview.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mx51.androidinterview.domain.model.WeatherDetails
import io.mx51.androidinterview.domain.interactor.GetWeatherDetailsUseCase
import io.mx51.androidinterview.domain.model.Units
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherDetailsUseCase: GetWeatherDetailsUseCase
): ViewModel() {

    private val _weatherDetails: MutableStateFlow<WeatherDetails?> = MutableStateFlow(null)
    val weatherDetails = _weatherDetails.asStateFlow()

    fun getWeatherDetails(unit: Units) {
        viewModelScope.launch(Dispatchers.IO) {
            getWeatherDetailsUseCase(unit).fold(
                onSuccess = {
                    _weatherDetails.value = it
                },
                onFailure = {

                }
            )

        }
    }
}