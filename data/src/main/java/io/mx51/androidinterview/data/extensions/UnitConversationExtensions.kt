package io.mx51.androidinterview.data.extensions

import io.mx51.androidinterview.data.model.*

/**
 * Created by jafaralrashid on 25,February,2022
 */

fun Double.kmPerHourToMeterPerSecond(): Double {
    return this * 0.277778
}

fun Double.meterPerSecondToMilesPerHour(): Double {
    return this * 2.23694
}

fun Double.milesPerHourToMetersPerSecond(): Double {
    return this * 0.44704
}

fun Double.meterPerSecondToKmPerHour(): Double {
    return this * 3.60000288
}

fun Double.celsiusToFahrenheit(): Double {
    return this.times(9/5) + 32
}

fun Double.fahrenheitToCelsius(): Double {
    return (this.minus(32)).times(5/9)
}

fun Temperature.convertTo(
    newUnit: TemperatureUnit
): Temperature {

    return when {
        this.unit == newUnit -> this
        newUnit == TemperatureUnit.Fahrenheit -> Temperature(
            value = this.value.celsiusToFahrenheit(),
            unit = newUnit
        )

        else -> Temperature(
            value = this.value.fahrenheitToCelsius(),
            unit = newUnit
        )
    }
}

fun Speed.convertTo(
    newUnit: SpeedUnit
): Speed {
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

        this.unit == SpeedUnit.MetersPerSecond && newUnit == SpeedUnit.MilesPerHour -> {
            Speed (
                value = this.value.meterPerSecondToMilesPerHour(),
                unit = newUnit
            )

        }

        this.unit == SpeedUnit.MilesPerHour && newUnit == SpeedUnit.MetersPerSecond -> {
            Speed (
                value = this.value.milesPerHourToMetersPerSecond(),
                unit = newUnit
            )

        }

        else -> throw NotImplementedError("Conversion from $unit convertTo $newUnit is not implemented yet")
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