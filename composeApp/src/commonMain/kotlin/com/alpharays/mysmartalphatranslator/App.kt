package com.alpharays.mysmartalphatranslator

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alpharays.smartalphatranslator.smartlang.TranslationViewModel

private val AppDarkColorScheme = darkColorScheme(
    primary = Color(0xFF9B5DFF),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1A1040),
    onPrimaryContainer = Color(0xFFF5F5FF),
    secondary = Color(0xFF4FC3F7),
    background = Color(0xFF0D0821),
    surface = Color(0xFF241560),
    onBackground = Color(0xFFF5F5FF),
    onSurface = Color(0xFFF5F5FF)
)

@Composable
fun App(translationViewModel: TranslationViewModel) {
    MaterialTheme(colorScheme = AppDarkColorScheme) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "home",
            enterTransition = {
                slideInHorizontally(tween(350)) { it } + fadeIn(tween(350))
            },
            exitTransition = {
                slideOutHorizontally(tween(350)) { -it / 3 } + fadeOut(tween(250))
            },
            popEnterTransition = {
                slideInHorizontally(tween(350)) { -it } + fadeIn(tween(350))
            },
            popExitTransition = {
                slideOutHorizontally(tween(350)) { it } + fadeOut(tween(250))
            }
        ) {
            composable("home") {
                HomeScreen(
                    onNavigateToPlayground = {
                        navController.navigate("playground")
                    }
                )
            }

            composable("playground") {
                TranslationPlaygroundScreen(
                    translationViewModel = translationViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}