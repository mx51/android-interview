package io.mx51.androidinterview.domain.interactor

import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.repository.WeatherDetailsRepository
import io.mx51.androidinterview.domain.model.WeatherDetails

class GetWeatherDetailsUseCase(
    private val weatherDetailsRepository: WeatherDetailsRepository
): UseCase<Units, Result<WeatherDetails>>() {

    override suspend fun run(params: Units): Result<WeatherDetails> {
        return weatherDetailsRepository.getWeatherDetails(params)
    }
}
