package io.mx51.androidinterview.data.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 9th of February 2022
 */
interface WeatherStackService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") apiKey: String,
        @Query("query") region: String,
        @Query("units") unit: String
    ): Response<WeatherStackDTO>
}