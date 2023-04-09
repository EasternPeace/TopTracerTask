package com.example.toptracer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toptracer.ui.WelcomeScreen

enum class AppSection() {
    Login, Welcome
}

@Composable
fun Application(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(
        modifier.fillMaxWidth()
    ) {
        NavHost(
            navController = navController,
            startDestination = AppSection.Login.name
        ) {
            composable(route = AppSection.Login.name) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(AppSection.Welcome.name)
                    }
                )
            }
            composable(route = AppSection.Welcome.name) {
                WelcomeScreen(
                    onLogoutClicked = {
                        navController.navigate(AppSection.Login.name)
                    }
                )
            }
        }
    }
}