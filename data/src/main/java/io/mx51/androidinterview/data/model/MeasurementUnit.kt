package io.mx51.androidinterview.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jafaralrashid on 25,February,2022
 */
enum class MeasurementUnit(
    val code: String
) {
    @SerializedName("m")
    Metric("m"),

    @SerializedName("f")
    Imperial("f")
}