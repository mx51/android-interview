package io.mx51.androidinterview.data.retrofit

import com.google.gson.annotations.SerializedName
import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.model.WeatherDetails

data class OpenWeatherMapDTO(
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("name")
    val name: String
) {
    data class Weather(
        @SerializedName("main")
        val main: String,
        @SerializedName("description")
        val description: String
    )

    data class Main(
        @SerializedName("temp")
        val temp: Double
    )

    data class Wind(
        @SerializedName("speed")
        val speed: Double
    )

    fun toWeatherDetails(units: Units) = WeatherDetails(
        units = units,
        temperature = main.temp,
        windSpeed = if (units == Units.IMPERIAL) {
            wind.speed / 2.237
        } else {
            wind.speed
        },
        description = weather.firstOrNull()?.main ?: "",
        locationName = name
    )
}