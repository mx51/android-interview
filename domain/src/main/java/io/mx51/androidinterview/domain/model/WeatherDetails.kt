package io.mx51.androidinterview.domain.model

enum class Units {
    STANDARD,
    METRIC,
    IMPERIAL
}

data class WeatherDetails(
    val units: Units,
    val temperature: Double,
    val windSpeed: Double, //always convert to meter/sec
    val description: String,
    val locationName: String
)