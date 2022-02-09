package io.mx51.androidinterview.data.impl

import io.mx51.androidinterview.data.BuildConfig
import io.mx51.androidinterview.domain.repository.WeatherDetailsRepository
import io.mx51.androidinterview.domain.model.WeatherDetails
import io.mx51.androidinterview.data.retrofit.OpenWeatherMapService
import io.mx51.androidinterview.data.retrofit.WeatherStackService
import io.mx51.androidinterview.domain.model.Units

class DefaultWeatherDetailsRepository(
    private val openWeatherMapService: OpenWeatherMapService,
    private val weatherStackService: WeatherStackService
) : WeatherDetailsRepository {
    override suspend fun getWeatherDetails(units: Units): Result<WeatherDetails> {
        return try {
            val response = openWeatherMapService.getCurrentWeather(getOpenWeatherUnit(units))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toWeatherDetails(units))
                } ?: Result.failure(Throwable("Something went wrong please try again"))
            } else {
                getWeatherDetailsFromSecondaryServer(units)
            }
        } catch (exception: Exception) {
            getWeatherDetailsFromSecondaryServer(units)
        }
    }

    private suspend fun getWeatherDetailsFromSecondaryServer(units: Units): Result<WeatherDetails> {
        return try {
            val response = weatherStackService.getCurrentWeather(
                apiKey = BuildConfig.WEATHER_STACK_API_KEY,
                region = "Sydney",
                unit = getWeatherStackUnit(units)
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    it.error?.let { error ->
                        Result.failure(Throwable(error.info))
                    } ?: Result.success(it.toWeatherDetails(units))
                } ?: Result.failure(Throwable("Something went wrong please try again"))
            } else
                Result.failure(Throwable("Something went wrong please try again"))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun getOpenWeatherUnit(units: Units): String {
        return when (units) {
            Units.METRIC -> {
                "metric" // Temperature-> Celsius, Wind Speed: meter/sec
            }
            Units.IMPERIAL -> {
                "imperial" // Temperature-> Fahrenheit, Wind Speed: miles/hour
            }
            else -> {
                "standard" // Temperature-> Kelvin, Wind Speed: meter/sec
            }
        }
    }


    private fun getWeatherStackUnit(units: Units): String {
        return when (units) {
            Units.METRIC -> {
                "m" //  Temperature-> Celsius, Wind Speed: Kilometers/Hour
            }
            Units.IMPERIAL -> {
                "f" // Temperature-> Fahrenheit, Wind Speed: Miles/Hour
            }
            else -> {
                "s" // Temperature-> Kelvin (Scientific), Wind Speed: Kilometers/Hour
            }
        }
    }
}