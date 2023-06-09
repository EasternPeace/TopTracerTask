package com.example.toptracer

import LoginViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toptracer.ui.LoginScreen
import com.example.toptracer.ui.WelcomeScreen
import com.example.toptracer.viewmodel.WelcomeViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

enum class AppSection {
    Login, Welcome
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopTracer(modifier: Modifier = Modifier) {
    val navController = rememberAnimatedNavController()
    val loginViewModel = viewModel<LoginViewModel>()
    val welcomeViewModel = viewModel<WelcomeViewModel>()

    Surface(
        modifier.fillMaxWidth()
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = AppSection.Login.name
        ) {
            composable(
                route = AppSection.Login.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                },
            ) {
                LoginScreen(
                    loginViewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate(AppSection.Welcome.name)
                    }
                )
            }
            composable(
                route = AppSection.Welcome.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                },
            ) {
                WelcomeScreen(
                    viewModel = welcomeViewModel,
                    onLogoutClicked = {
                        navController.navigate(AppSection.Login.name)
                    }
                )
            }
        }
    }
}