package io.mx51.androidinterview.domain.repository

import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.model.WeatherDetails

interface WeatherDetailsRepository {
    suspend fun getWeatherDetails(units: Units): Result<WeatherDetails>
}