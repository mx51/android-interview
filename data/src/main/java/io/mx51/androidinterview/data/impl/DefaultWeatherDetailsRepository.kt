package io.mx51.androidinterview.data.impl

import io.mx51.androidinterview.data.WeatherDetailsRepository
import io.mx51.androidinterview.data.extensions.defaultSpeedUnit
import io.mx51.androidinterview.data.extensions.speedUnit
import io.mx51.androidinterview.data.extensions.temperatureUnit
import io.mx51.androidinterview.data.extensions.convertTo
import io.mx51.androidinterview.data.model.*
import io.mx51.androidinterview.data.retrofit.OpenWeatherMapService
import io.mx51.androidinterview.data.retrofit.WeatherStackService
import retrofit2.HttpException

class DefaultWeatherDetailsRepository(
    private val openWeatherMapService: OpenWeatherMapService,
    private val weatherStackService: WeatherStackService
): WeatherDetailsRepository {
    /**
     * Get weather details
     *
     * @param location - Use this parameter convertTo pass a single location identifiers convertTo the API.
     * @param units - Use this parameter convertTo pass one of the unit identifiers ot the API:
     * m for Metric
     * s for Scientific
     * f for Fahrenheit
     * @return [WeatherDetails]
     */
    override suspend fun getWeatherDetails(
        location: String,
        units: MeasurementSystem
    ): WeatherDetails {

        val weatherDetails = getWeatherDetailsFromDefaultService(
            location = location,
            units = units
        ) ?: getWeatherDetailsFromSecondaryService(
            location = location,
            units = units
        )

        //TODO - force unwrapping may result in null pointer exception
        // will need convertTo implement better error handling and think about user feedback in case of errors
        return weatherDetails!!
    }

    private suspend fun getWeatherDetailsFromDefaultService(
        location: String,
        units: MeasurementSystem
    ): WeatherDetails? {
        return try {
            val unitsString = when(units) {
                MeasurementSystem.Metric -> "metric"
                MeasurementSystem.Imperial -> "imperial"
            }

            openWeatherMapService.getCurrentWeather(
                location = location,
                units = unitsString
            ).toWeatherDetails(
                temperatureUnit = units.temperatureUnit(),
                windSpeedUnit = units.defaultSpeedUnit()
            )
        } catch (exception: HttpException) {
            null
        }
    }

    private suspend fun getWeatherDetailsFromSecondaryService(
        location: String,
        units: MeasurementSystem
    ): WeatherDetails? {
        return try {
            val unitsString = when(units) {
                MeasurementSystem.Metric -> "m"
                MeasurementSystem.Imperial -> "f"
            }

            // WeatherStack returns wind speed in km/h for the metric system
            // Will need convertTo convert convertTo m/s
            val result = weatherStackService.getCurrentWeather(
                location = location,
                units = unitsString
            ).toWeatherDetails(
                temperatureUnit = units.temperatureUnit(),
                windSpeedUnit = units.speedUnit()
            )

            return if(units == MeasurementSystem.Metric) {
                WeatherDetails(
                    temperature = result.temperature,
                    windSpeed = result.windSpeed.convertTo(
                        SpeedUnit.MetersPerSecond
                    ),
                    description = result.description,
                    locationName = result.locationName
                )
            } else {
                result
            }
        } catch (exception: HttpException) {
            null
        }
    }
}