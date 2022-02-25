package io.mx51.androidinterview.data.retrofit

import com.google.gson.annotations.SerializedName
import io.mx51.androidinterview.data.model.MeasurementUnit
import io.mx51.androidinterview.data.model.WeatherDetails

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

    fun toWeatherDetails() = WeatherDetails(
        temperature = main.temp,
        windSpeed = wind.speed,
        description = weather.firstOrNull()?.main ?: "",
        locationName = name,
        measurementUnit = MeasurementUnit.Metric //TODO - get this from the open weather response
    )
}