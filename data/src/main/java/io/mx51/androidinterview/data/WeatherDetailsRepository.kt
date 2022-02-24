package io.mx51.androidinterview.data

import io.mx51.androidinterview.data.model.WeatherDetails

interface WeatherDetailsRepository {
    suspend fun getWeatherDetails(
        location: String,
        units: String
    ): WeatherDetails
}