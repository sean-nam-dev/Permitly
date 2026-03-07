package com.sean.permitly.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sean.permitly.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<AuthGraph>(
            startDestination = AuthRoute.Onboarding
        ) {
            composable<AuthRoute.Onboarding> {
                OnboardingScreen(
                    viewModel = hiltViewModel(),
                    navigateToLogin = {
                        navController.navigate(
                            route = AuthRoute.Login
                        )
                    }
                )
            }
            composable<AuthRoute.Login> {

            }
        }

        navigation<MainGraph>(
            startDestination = MainRoute.Home
        ) {
            composable<MainRoute.Home> {

            }
        }
    }
}