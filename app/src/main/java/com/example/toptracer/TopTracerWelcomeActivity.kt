package com.example.toptracer

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.toptracer.helpers.GiphyImageDetails
import com.example.toptracer.helpers.fetchRandomGif
import com.example.toptracer.ui.theme.TopTracerTheme

class TopTracerWelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopTracerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, toptracer!"
        )
        RandomGif(
        )
        Text(
            text = "GifTitle by GifAuthor!"
        )
        Text(
            text = "Logout",
            modifier = Modifier
                .clickable {}
        )
    }
}

@Composable
fun RandomGif() {
    val apiKey = "vYbi41ARNKCZHJvrZ7IlDdqfCGSb8ZZy"
    val imageDetails = remember { mutableStateOf<GiphyImageDetails?>(null) }

    LaunchedEffect(apiKey) {
        imageDetails.value = fetchRandomGif(apiKey)
    }

    imageDetails.value?.let {gif ->
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()


        Image(
            painter = rememberAsyncImagePainter(gif.url, imageLoader),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
        )
    }
}
