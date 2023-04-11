package com.example.toptracer.ui

import LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.toptracer.R
import com.example.toptracer.helpers.CircularProgressBar
import com.example.toptracer.helpers.ClickableText
import com.example.toptracer.helpers.RandomGif
import com.example.toptracer.viewmodel.GifViewModel

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    onLogoutClicked: () -> Unit
) {
    val viewModel = remember { GifViewModel() }
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(orientation) {
        viewModel.getRandomGif()
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
            RandomGif(url = viewModel.url.value)
            CircularProgressBar(isDisplayed = viewModel.isLoading.value)
        }
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = viewModel.prettyTitle.value
        )
        Spacer(modifier = modifier.height(8.dp))
        ClickableText(text = stringResource(R.string.logout)) {
            loginViewModel.logout()
            onLogoutClicked()
        }
    }
}
