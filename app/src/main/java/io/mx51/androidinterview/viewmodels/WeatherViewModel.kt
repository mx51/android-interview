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

    sealed class State {
        object Loading: State()
        data class Loaded(val weatherDetails: WeatherDetails): State()
        data class LoadError(val message: String?, val unit: Units): State()
    }

    private val _uiState:MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val uiState = _uiState.asStateFlow()


    fun getWeatherDetails(unit: Units) {
        _uiState.value = State.Loading // every time request for weather details display the loading progress
        viewModelScope.launch(Dispatchers.IO) {
            getWeatherDetailsUseCase(unit).fold(
                onSuccess = {
                    _uiState.value = State.Loaded(it)
                },
                onFailure = {
                    _uiState.value = State.LoadError(it.message, unit)
                }
            )
        }
    }
}