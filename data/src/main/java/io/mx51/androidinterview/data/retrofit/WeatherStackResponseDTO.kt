package io.mx51.androidinterview.data.retrofit

import com.google.gson.annotations.SerializedName
import io.mx51.androidinterview.data.model.WeatherDetails

data class WeatherStackResponseDTO(
    @SerializedName("current")
    val weather: CurrentWeather,

    @SerializedName("location")
    val location: Location,

    @SerializedName("request")
    val request: CurrentWeatherRequest
) {

    data class CurrentWeatherRequest(
        @SerializedName("unit")
        val units: String
    )

    data class CurrentWeather(
        @SerializedName("temperature")
        val temperature: Double,

        @SerializedName("wind_speed")
        val windSpeed: Double,

        @SerializedName("weather_descriptions")
        val descriptions: List<String>
    )
    data class Location(
        @SerializedName("name")
        val name: String
    )

    fun toWeatherDetails() = WeatherDetails(
        temperature = weather.temperature,
        windSpeed = weather.windSpeed,
        description = weather.descriptions.firstOrNull() ?: "",
        locationName = location.name
    )
}