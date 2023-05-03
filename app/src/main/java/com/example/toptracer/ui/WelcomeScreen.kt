package com.example.toptracer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.toptracer.R
import com.example.toptracer.helpers.CircularProgressBar
import com.example.toptracer.helpers.ClickableText
import com.example.toptracer.helpers.RandomGif
import com.example.toptracer.viewmodel.WelcomeViewModel

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel,
    onLogoutClicked: () -> Unit
) {
    val gifUiState by viewModel.gifUiState.collectAsState()
    LaunchedEffect(gifUiState.url) {
        if (gifUiState.url.isEmpty()) {
            viewModel.getRandomGif()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome_title)
        )
        Spacer(modifier = modifier.height(8.dp))
        Box(
            contentAlignment = Alignment.Center
        ) {
            RandomGif(url = gifUiState.url)
            CircularProgressBar(isDisplayed = gifUiState.isLoading)
        }
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = viewModel.prettyTitle.value
        )
        Spacer(modifier = modifier.height(8.dp))
        ClickableText(text = stringResource(R.string.logout)) {
            viewModel.resetGifState()
            onLogoutClicked()
        }
    }
}
