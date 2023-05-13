package com.example.toptracer.helpers

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.toptracer.R
import com.example.toptracer.ui.theme.ui.theme.Main

@Composable
fun ClickableText(text: String, modifier: Modifier = Modifier, onClicked: () -> Unit) {
    Text(
        text = text,
        color = Main,
        modifier = modifier
            .clickable {
                onClicked()
            }
    )
}

@Composable
fun ValidationAlert(showDialog: Boolean, dialogText: String, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(R.string.oops),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = { Text(dialogText) },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    content = { Text(text = stringResource(R.string.ok)) }
                )
            }
        )
    }
}

@Composable
fun InputRow(
    name: String,
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = modifier.padding(start = 4.dp, end = 4.dp)
        )
        TextField(
            value = input,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        )
    }
}

@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        CircularProgressIndicator(
            color = Main
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
        contentDescription = "Random Gif",
        modifier = modifier
            .size(200.dp)
            .clip(RectangleShape)
    )
}
