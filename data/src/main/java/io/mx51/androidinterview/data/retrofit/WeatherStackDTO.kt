package io.mx51.androidinterview.data.retrofit

import com.google.gson.annotations.SerializedName
import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.model.WeatherDetails

/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 9th of February 2022
 */
data class WeatherStackDTO(
    val error: Error?,
    val request: Request,
    val location: Location,
    val current: Current
) {

    data class Error(
        val code: Int,
        val type: String,
        val info: String
    )

    data class Request(
        val type: String,
        val query: String,
        val language: String,
        val unit: String
    )

    data class Location(
        val name: String,
        val country: String,
        val region: String,
        val lat: Double,
        val lon: Double,
        @SerializedName("timezone_id")
        val timezoneId: String,
        val localtime: String,
        @SerializedName("localtime_epoch")
        val localtimeEpoch: Long,
        @SerializedName("utc_offset")
        val utcOffset: String
    )

    data class Current(
        @SerializedName("observation_time")
        val observationTime: String,
        val temperature: Double,
        @SerializedName("weather_code")
        val weatherCode: Int,
        @SerializedName("weather_icons")
        val weatherIcons: List<String>,
        @SerializedName("weather_descriptions")
        val weatherDescriptions: List<String>,
        @SerializedName("wind_speed")
        val windSpeed: Double, //Returns the wind speed in the selected unit. (Default: kilometers/hour)
        @SerializedName("wind_degree")
        val windDegree: Double,
        @SerializedName("wind_dir")
        val windDir: String,
        val pressure: Double, // Returns the air pressure in the selected unit. (Default: MB - millibar)
        val precip: Double, // Returns the precipitation level in the selected unit. (Default: MM - millimeters)
        val humidity: Double, // Returns the air humidity level in percentage.
        val cloudcover: Double, //Returns the cloud cover level in percentage.
        val feelslike: Int, //Returns the "Feels Like" temperature in the selected unit. (Default: Celsius)
        @SerializedName("uv_index")
        val uvIndex: Int, //Returns the UV index associated with the current weather condition.
        val visibility: Double //Returns the visibility level in the selected unit. (Default: kilometers)
    )


    fun toWeatherDetails(units: Units) = WeatherDetails(
        units = units,
        temperature = current.temperature,
        windSpeed =
        if (units == Units.IMPERIAL) { //Miles/Hour
            current.windSpeed / 2.237
        } else { // Kilometers/Hour
            current.windSpeed / 3.6
        },
        description = current.weatherDescriptions.firstOrNull() ?: "",
        locationName = location.name
    )
}
