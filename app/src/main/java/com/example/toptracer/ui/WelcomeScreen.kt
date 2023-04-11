package com.example.toptracer.ui

import LoginViewModel
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "Welcome, toptracer!"
        )
        Box(contentAlignment = Alignment.Center) {
            RandomGif(url = viewModel.url.value)
            CircularProgressBar(isDisplayed = viewModel.isLoading.value)
        }

        Text(
            text = viewModel.prettyTitle.value
        )
    }

    Text(
        text = "Logout",
        modifier = Modifier
            .clickable {
                loginViewModel.logout() // Clear username and password
                onLogoutClicked()
            }
    )
}


@Composable
fun RandomGif(
    modifier: Modifier = Modifier,
    url: String
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(url, imageLoader),
        contentDescription = "Random Gif",
        modifier = modifier
            .size(200.dp)
            .clip(RectangleShape)
    )
}

@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}