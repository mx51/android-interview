package io.mx51.androidinterview.data.impl

import io.mx51.androidinterview.data.BuildConfig
import io.mx51.androidinterview.data.retrofit.OpenWeatherMapService
import io.mx51.androidinterview.data.retrofit.WeatherStackService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherServiceProviders {

    private val client: OkHttpClient

    init {
        val builder = OkHttpClient().newBuilder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        client = builder.build()
    }

    fun provideOpenWeatherMapService(): OpenWeatherMapService {
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherMapService::class.java)
    }

    fun provideWeatherStackService(): WeatherStackService {
        return Retrofit.Builder()
            .baseUrl("http://api.weatherstack.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherStackService::class.java)
    }
}