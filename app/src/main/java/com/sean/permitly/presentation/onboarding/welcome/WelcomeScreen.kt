package com.sean.permitly.presentation.onboarding.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.presentation.onboarding.OnBoardingEvent
import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun WelcomeScreen(viewModel: OnBoardingViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

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
        step = state.value.step,
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