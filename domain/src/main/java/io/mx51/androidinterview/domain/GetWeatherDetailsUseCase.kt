package io.mx51.androidinterview.domain

import io.mx51.androidinterview.data.WeatherDetailsRepository
import io.mx51.androidinterview.data.model.WeatherDetails

class GetWeatherDetailsUseCase(
    private val weatherDetailsRepository: WeatherDetailsRepository
): UseCase<() -> WeatherRequestParameters, WeatherDetails>() {

    override suspend fun run(
        params: () -> WeatherRequestParameters
    ): WeatherDetails {

        return params.invoke().let {
            weatherDetailsRepository.getWeatherDetails(
                it.location,
                it.units
            )
        }
    }
}

data class WeatherRequestParameters(
    val location: String = "New York",
    val units: String = "m"
)
