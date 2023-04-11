package com.example.toptracer.ui

import LoginViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.toptracer.R
import com.example.toptracer.helpers.ClickableText
import com.example.toptracer.helpers.InputRow
import com.example.toptracer.helpers.TopTracerPasswordTransformation
import com.example.toptracer.helpers.ValidationAlert


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
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
        InputRow(
            name = stringResource(id = R.string.username),
            input = username,
            onValueChange = viewModel::onUsernameChanged,
            visualTransformation = VisualTransformation.None
        )
        InputRow(
            name = stringResource(id = R.string.password),
            input = password,
            onValueChange = viewModel::onPasswordChanged,
            visualTransformation = TopTracerPasswordTransformation()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .clickable {/* TODO: */ }
            )

            ClickableText(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
            ) {
                viewModel.login(
                    onSuccess = onLoginSuccess,
                    onError = { errorText ->
                        showDialog = true
                        dialogText = errorText
                    }
                )
            }
        }
    }
    ValidationAlert(showDialog, dialogText) {
        showDialog = false
    }
}