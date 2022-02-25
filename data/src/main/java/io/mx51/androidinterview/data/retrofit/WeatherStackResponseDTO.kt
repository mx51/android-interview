package io.mx51.androidinterview.data.retrofit

import com.google.gson.annotations.SerializedName
import io.mx51.androidinterview.data.model.SpeedUnit
import io.mx51.androidinterview.data.model.TemperatureUnit
import io.mx51.androidinterview.data.model.WeatherDetails

data class WeatherStackResponseDTO(
    @SerializedName("current")
    val weather: CurrentWeather,

    @SerializedName("location")
    val location: Location
) {

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

    fun toWeatherDetails(
        temperatureUnit: TemperatureUnit,
        windSpeedUnit: SpeedUnit
    ) = WeatherDetails(
        temperatureUnit = temperatureUnit,
        windSpeedUnit = windSpeedUnit,
        temperature = weather.temperature,
        windSpeed = weather.windSpeed,
        description = weather.descriptions.firstOrNull() ?: "",
        locationName = location.name
    )
}