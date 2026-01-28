package com.sean.permitly.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { Step.entries.size }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                OnBoardingEvent.Navigate -> {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        val targetPage = pagerState.currentPage + 1
                        viewModel.changeStep(Step.entries[targetPage])
                        pagerState.animateScrollToPage(
                            page = targetPage,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    } else {
                        // Navigate to Login
                    }
                }
            }
        }
    }

    OnBoardingUI(
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
        onNextClick = viewModel::onNextClick
    )
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    PermitlyTheme {
        OnBoardingScreen(
            viewModel = OnBoardingViewModel()
        )
    }
}