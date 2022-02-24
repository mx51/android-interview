package io.mx51.androidinterview.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {
    @GET("data/2.5/weather?appid=2326504fb9b100bee21400190e4dbe6d")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("units") units: String
    ): OpenWeatherMapDTO
}