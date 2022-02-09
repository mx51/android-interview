package io.mx51.androidinterview.domain.interactor

import io.mockk.coEvery
import io.mockk.mockk
import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.model.WeatherDetails
import io.mx51.androidinterview.domain.repository.WeatherDetailsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test


/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 10th of February 2022
 */
class GetWeatherDetailsUseCaseTest {

    private val repository: WeatherDetailsRepository = mockk()

    private val getWeatherDetailsUseCase = GetWeatherDetailsUseCase(repository)

    @Test
    fun `test get weather details failure case`() = runBlocking {
        coEvery { repository.getWeatherDetails(any()) } returns Result.failure(Throwable("No internet connection"))

        val result = getWeatherDetailsUseCase.invoke(Units.METRIC)

        assert(result.isFailure)
    }

    @Test
    fun `test get weather details success case`() = runBlocking {
        coEvery { repository.getWeatherDetails(any()) } returns Result.success(WeatherDetails(Units.IMPERIAL, 27.0, 45.0, "Clear Sky", "Sydney"))

        val result = getWeatherDetailsUseCase.invoke(Units.IMPERIAL)

        assert(result.isSuccess)
    }

}