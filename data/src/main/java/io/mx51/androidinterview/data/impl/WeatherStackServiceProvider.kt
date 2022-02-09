package io.mx51.androidinterview.data.impl

import io.mx51.androidinterview.data.retrofit.WeatherStackService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 9th of February 2022
 */
object WeatherStackServiceProvider {

    fun provideWeatherStackService() : WeatherStackService {
        return Retrofit.Builder()
            .baseUrl("http://api.weatherstack.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherStackService::class.java)
    }
}