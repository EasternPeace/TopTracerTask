package com.example.toptracer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.toptracer.helpers.TopTracerPasswordTransformation

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isUsernameEmpty by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    Column(
        modifier = modifier .padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UsernameRow(username = username) { usernameInput ->
            username = usernameInput
            isUsernameEmpty = usernameInput.isEmpty()
        }
        PasswordRow(password = password) { passwordInput ->
            password = passwordInput
            isPasswordValid = passwordInput == "password"
        }

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
                        if (isUsernameEmpty) {
                            showDialog = true
                            dialogText = "It looks like you forgot to provide a username."
                        } else if (isPasswordValid) {
                            onLoginSuccess()
                        } else {
                            showDialog = true
                            dialogText = "The password you provided doesn't match our records."
                        }
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