package com.sean.permitly.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    navigateToLogin: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(state.value.step.index) {
        Step.entries.size
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                OnboardingEvent.Navigate -> {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    } else {
                        navigateToLogin()
                    }
                }
            }
        }
    }



    OnboardingUI(
        pagerState = pagerState,
        step = state.value.step,
        agreementPageData = AgreementPageData(
            isAgreementAccepted = state.value.isAgreementAccepted,
            onAgreementClick = viewModel::onAgreementClick
        ),
        statesPageData = StatesPageData(
            examState = state.value.examState,
            onRadioClick = viewModel::onRadioClick
        ),
        onNextClick = viewModel::onNextClick,
        onStepChange = viewModel::onStepChange
    )
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    val viewModel = viewModel<OnboardingViewModel>()

    PermitlyTheme {
        OnboardingScreen(
            viewModel = viewModel,
            navigateToLogin = {},
        )
    }
}