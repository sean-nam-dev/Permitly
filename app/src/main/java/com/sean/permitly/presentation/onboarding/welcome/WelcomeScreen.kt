package com.sean.permitly.presentation.onboarding.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.presentation.onboarding.OnBoardingEvent
import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun WelcomeScreen(viewModel: OnBoardingViewModel) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                OnBoardingEvent.Navigate -> {

                }
                else -> Unit
            }
        }
    }

    WelcomeUI(
        onNextClick = viewModel::onNextClick
    )
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    PermitlyTheme {
        WelcomeScreen(
            viewModel = OnBoardingViewModel()
        )
    }
}