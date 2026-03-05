package com.sean.permitly.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.Step

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
                OnboardingEvent.Next -> {
                    pagerState.animateScrollToPage(
                        page = state.value.step.index,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
                OnboardingEvent.Navigate -> {
                    navigateToLogin()
                }
            }
        }
    }

    OnboardingUI(
        pagerState = pagerState,
        agreementPageData = AgreementPageData(
            isAgreementAccepted = state.value.isAgreementAccepted,
            onAgreementClick = viewModel::onAgreementClick
        ),
        statesPageData = StatesPageData(
            examState = state.value.examState,
            onRadioClick = viewModel::onRadioClick
        ),
        isPrimaryButtonEnabled = state.value.isPrimaryButtonEnabled,
        onPrimaryButtonClick = viewModel::onPrimaryButtonClick
    )
}