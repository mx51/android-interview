package io.mx51.androidinterview.data.impl

import io.mx51.androidinterview.data.WeatherDetailsRepository
import io.mx51.androidinterview.data.model.WeatherDetails
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
     * @param location - Use this parameter to pass a single location identifiers to the API.
     * @param units - Use this parameter to pass one of the unit identifiers ot the API:
     * m for Metric
     * s for Scientific
     * f for Fahrenheit
     * @return [WeatherDetails]
     */
    override suspend fun getWeatherDetails(
        location: String,
        units: String
    ): WeatherDetails {

        //TODO - use the [openWeatherMapService] to fetch the weather first and fail-over to [weatherStackService]
        // in case of errors
        return try {
            weatherStackService.getCurrentWeather(
                location = location,
                units = units
            ).toWeatherDetails()
        } catch (exception: HttpException) {
            WeatherDetails(
                temperature = 0.0,
                windSpeed = 0.0,
                description = "",
                locationName = ""
            )
        }
    }
}