package com.example.toptracer

import LoginViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.toptracer.helpers.TopTracerPasswordTransformation


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val username by viewModel.username
    val password by viewModel.password

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var dialogText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UsernameRow(username = username, onValueChange = viewModel::onUsernameChanged)
        PasswordRow(password = password, onValueChange = viewModel::onPasswordChanged)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Forgot Password",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .clickable {}
            )

            Text(
                text = "Login",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .clickable {
                        viewModel.login(
                            onSuccess = onLoginSuccess,
                            onError = { errorText ->
                                showDialog = true
                                dialogText = errorText
                            }
                        )
                    }
            )
        }
    }
    ValidationAlert(showDialog, dialogText) {
        showDialog = false
    }
}


@Composable
fun UsernameRow(username: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Username",
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = username,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier.weight(3f),
        )
    }
}

@Composable
fun PasswordRow(password: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Password",
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = password,
            onValueChange = onValueChange,
            visualTransformation = TopTracerPasswordTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .weight(3f)
        )
    }
}

@Composable
fun ValidationAlert(showDialog: Boolean, dialogText: String, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Oops!",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = { Text(dialogText) },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    content = { Text("OK") }
                )
            }
        )
    }
}