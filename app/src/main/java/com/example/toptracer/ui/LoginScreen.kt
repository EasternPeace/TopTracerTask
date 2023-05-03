package com.example.toptracer.ui

import LoginViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
) {
    val userUiState by loginViewModel.loginScreenUiState.collectAsState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InputRow(
            name = stringResource(id = R.string.username),
            input = loginViewModel.username,
            onValueChange = { loginViewModel.updateUsername(it) },
            visualTransformation = VisualTransformation.None
        )
        InputRow(
            name = stringResource(id = R.string.password),
            input = loginViewModel.password,
            onValueChange = { loginViewModel.updatePassword(it) },
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
                loginViewModel.onLoginClicked(onLoginSuccess)
            }
        }
    }
    ValidationAlert(userUiState.isError, userUiState.errorText) {
        loginViewModel.resetErrorState()
    }
}