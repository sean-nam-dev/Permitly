package com.sean.permitly.presentation.onboarding.states

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun StatesScreen(viewModel: OnBoardingViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    StatesUI(
        examState = state.value.examState,
        onRadioClick = viewModel::onRadioClick
    )
}

@Preview
@Composable
private fun StatesScreenPreview() {
    PermitlyTheme {
        StatesScreen(
            viewModel = OnBoardingViewModel()
        )
    }
}