package io.mx51.androidinterview.data.extensions

import io.mx51.androidinterview.data.model.MeasurementSystem
import io.mx51.androidinterview.data.model.Speed
import io.mx51.androidinterview.data.model.SpeedUnit
import io.mx51.androidinterview.data.model.TemperatureUnit

/**
 * Created by jafaralrashid on 25,February,2022
 */

fun Double.kmPerHourToMeterPerSecond(): Double {
    return this * 0.277778
}

fun Double.meterPerSecondToKmPerHour(): Double {
    return this * 3.60000288
}

fun Speed.to(newUnit: SpeedUnit): Speed {
    return when {
        newUnit == this.unit -> this
        this.unit == SpeedUnit.MetersPerSecond && newUnit == SpeedUnit.KiloMetersPerHour -> {
            Speed(
                this.value.meterPerSecondToKmPerHour(),
                newUnit
            )
        }

        this.unit == SpeedUnit.KiloMetersPerHour && newUnit == SpeedUnit.MetersPerSecond -> {
            Speed(
                this.value.kmPerHourToMeterPerSecond(),
                newUnit
            )
        }

        else -> throw NotImplementedError("Conversion from $unit to $newUnit is not implemented yet")
    }
}

/**
 * Temperature unit from this measurement system
 *
 * @return [TemperatureUnit] -
 * [TemperatureUnit.Fahrenheit] for [MeasurementSystem.Imperial] and
 * [TemperatureUnit.Celsius] for [MeasurementSystem.Metric]
 */
fun MeasurementSystem.temperatureUnit(): TemperatureUnit {
    return when(this) {
        MeasurementSystem.Metric -> TemperatureUnit.Celsius
        MeasurementSystem.Imperial -> TemperatureUnit.Fahrenheit
    }
}

/**
 * Default speed unit from this measurement system
 *
 * @return - [SpeedUnit]
 * [SpeedUnit.MetersPerSecond] for [MeasurementSystem.Metric] and
 * [SpeedUnit.MilesPerHour] for [MeasurementSystem.Imperial]
 */
fun MeasurementSystem.defaultSpeedUnit(): SpeedUnit {
    return when(this) {
        MeasurementSystem.Metric -> SpeedUnit.MetersPerSecond
        MeasurementSystem.Imperial -> SpeedUnit.MilesPerHour
    }
}

/**
 * Default speed unit from this measurement system
 *
 * @return - [SpeedUnit]
 * [SpeedUnit.KiloMetersPerHour] for [MeasurementSystem.Metric] and
 * [SpeedUnit.MilesPerHour] for [MeasurementSystem.Imperial]
 */
fun MeasurementSystem.speedUnit(): SpeedUnit {
    return when(this) {
        MeasurementSystem.Metric -> SpeedUnit.KiloMetersPerHour
        MeasurementSystem.Imperial -> SpeedUnit.MilesPerHour
    }
}