package io.mx51.androidinterview.app_ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mx51.androidinterview.app_ui.theme.AndroidInterviewTheme
import io.mx51.app_ui.R

@Composable
fun WeatherDetailsScreen(
    isImperialUnit: Boolean = false,
    locationName: String,
    temperature: Double,
    windSpeed: Double,
    description: String,
    onRefreshClicked: () -> Unit
) {
    AndroidInterviewTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.weather_location_title_label, locationName),
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val text = if (isImperialUnit) {
                            stringResource(R.string.temperature_fahrenheit_label, temperature)
                        } else {
                            stringResource(R.string.temperature_celsius_label, temperature)
                        }
                        Text(
                            text = text,
                            modifier = Modifier.padding(top = 5.dp),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp,
                        )
                        Text(
                            text = stringResource(R.string.wind_label, windSpeed),
                            modifier = Modifier.padding(top = 2.dp),
                            fontSize = 18.sp,
                        )
                    }
                    Text(
                        text = description,
                        modifier = Modifier.padding(top = 5.dp),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Button(
                    onClick = onRefreshClicked,
                    modifier = Modifier
                        .padding(top = 25.dp)
                ) {
                    Text(
                        text = stringResource(R.string.refresh_btn),
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherDetailsScreenPreview() {
    AndroidInterviewTheme {
        WeatherDetailsScreen(
            temperature = 32.45,
            windSpeed = 2.57,
            description = "Sunny",
            locationName = "Sydney",
            onRefreshClicked = { }
        )
    }
}