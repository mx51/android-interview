package io.mx51.androidinterview.data.model

data class WeatherDetails(
    val temperature: Temperature,
    val windSpeed: Speed,
    val description: String,
    val locationName: String
) {
    constructor(
        temperatureUnit: TemperatureUnit,
        windSpeedUnit: SpeedUnit,
        temperature: Double,
        windSpeed: Double,
        description: String,
        locationName: String
    ): this (
            temperature = Temperature(
                value = temperature,
                unit = temperatureUnit
            ),

            windSpeed = Speed(
                value = windSpeed,
                unit = windSpeedUnit
            ),

            description = description,
            locationName = locationName,

        )
}