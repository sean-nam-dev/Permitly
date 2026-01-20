package com.sean.permitly.presentation.onboarding.agreement

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun AgreementScreen(viewModel: OnBoardingViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    AgreementUI(
        isAgreementAccepted = state.value.isAgreementAccepted,
        onAgreementClick = viewModel::onAgreementClick,
        onNextClick = viewModel::onNextClick
    )
}

@Preview
@Composable
private fun AgreementScreenPreview() {
    PermitlyTheme {
        AgreementScreen(
            viewModel = OnBoardingViewModel()
        )
    }
}