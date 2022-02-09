package io.mx51.androidinterview.app_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 10th of February 2022
 */
@Composable
fun ErrorScreen(
    message: String?,
    onRetryClicked: () -> Unit
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
                val errorMessage = message ?: stringResource(R.string.default_api_error_message)
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                )

                Button(
                    onClick = onRetryClicked,
                    modifier = Modifier
                        .padding(top = 25.dp)
                ) {
                    Text(
                        text = stringResource(R.string.retry_btn),
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
fun ErrorScreenPreview() {
    AndroidInterviewTheme {
        ErrorScreen(
            message = "Something went wrong. Please try again later.",
            onRetryClicked = {}
        )
    }
}