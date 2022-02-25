package io.mx51.androidinterview.data.model

data class WeatherDetails(
    val temperature: Double,
    val windSpeed: Double,
    val measurementUnit: MeasurementUnit,
    val description: String,
    val locationName: String
)