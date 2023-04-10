package com.example.toptracer.ui

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.toptracer.viewmodel.GifViewModel

@Composable
fun WelcomeScreen(
    viewModel: GifViewModel,
    modifier: Modifier = Modifier,
    onLogoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(Unit) {
            viewModel.getRandomGif()
        }



        Text(
            text = "Welcome, toptracer!"
        )

        RandomGif(url = viewModel.url.value)

        Text(
            text = "${viewModel.title.value} by ${viewModel.username.value}"
        )
        Text(
            text = "Logout",
            modifier = Modifier
                .clickable {
                    onLogoutClicked()
                }
        )
    }
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
        contentDescription = null,
        modifier = modifier
            .size(200.dp)
            .clip(RectangleShape)
    )
}