package io.mx51.androidinterview

import android.app.Application
import io.mx51.androidinterview.viewmodels.WeatherViewModel
import io.mx51.androidinterview.data.WeatherDetailsRepository
import io.mx51.androidinterview.data.impl.DefaultWeatherDetailsRepository
import io.mx51.androidinterview.data.impl.WeatherServiceProviders
import io.mx51.androidinterview.domain.GetWeatherDetailsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidInterviewApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                module {
                    single<WeatherDetailsRepository> {
                        DefaultWeatherDetailsRepository(
                            get(),
                            get()
                        )
                    }
                    single {
                        GetWeatherDetailsUseCase(get())
                    }
                    single {
                        WeatherServiceProviders.provideOpenWeatherMapService()
                    }

                    single {
                        WeatherServiceProviders.provideWeatherStackService()
                    }

                    viewModel {
                        WeatherViewModel(get())
                    }
                }
            )
        }
    }
}