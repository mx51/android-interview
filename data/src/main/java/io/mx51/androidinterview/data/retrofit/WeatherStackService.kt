package io.mx51.androidinterview.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_STACK_API_KEY = "4beb5ca49d7c36fbc1cc066a421c6e46"

interface WeatherStackService {
    @GET("current?access_key=$WEATHER_STACK_API_KEY")
    suspend fun getCurrentWeather(
        @Query("query") location: String,
        @Query("units") units: String
    ): WeatherStackResponseDTO
}