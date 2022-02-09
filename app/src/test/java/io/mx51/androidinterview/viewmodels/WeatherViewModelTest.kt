package io.mx51.androidinterview.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import io.mx51.androidinterview.domain.interactor.GetWeatherDetailsUseCase
import io.mx51.androidinterview.domain.model.Units
import io.mx51.androidinterview.domain.model.WeatherDetails
import io.mx51.androidinterview.domain.repository.WeatherDetailsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 10th of February 2022
 */
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel

    private val weatherDetailsRepository: WeatherDetailsRepository = mockk()

    private val getWeatherDetailsUseCase =
        GetWeatherDetailsUseCase(weatherDetailsRepository = weatherDetailsRepository)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(getWeatherDetailsUseCase = getWeatherDetailsUseCase)
    }

    @Test
    fun `Get weather details fail should set the viw model ui state to LoadError`() = runBlocking {
        coEvery { weatherDetailsRepository.getWeatherDetails(any()) } returns Result.failure(
            Throwable("No internet connection")
        )

        viewModel.getWeatherDetails(Units.IMPERIAL)

        viewModel.uiState.test {
            expectMostRecentItem() is  WeatherViewModel.State.LoadError
        }
    }

    @Test
    fun `Get weather details success should set the view model ui state to Loaded`() = runBlocking {
        coEvery { weatherDetailsRepository.getWeatherDetails(any()) } returns
                Result.success(WeatherDetails(Units.METRIC, 27.0, 45.0, "Clear Sky", "Sydney"))

        viewModel.getWeatherDetails(Units.METRIC)

        viewModel.uiState.test {
            expectMostRecentItem() is  WeatherViewModel.State.Loaded
        }
    }
}