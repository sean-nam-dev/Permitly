package com.sean.permitly.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.R
import com.sean.permitly.presentation.component.NavigationProgress
import com.sean.permitly.presentation.component.PrimaryButton
import com.sean.permitly.presentation.onboarding.agreement.AgreementUI
import com.sean.permitly.presentation.onboarding.states.StatesUI
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.presentation.onboarding.welcome.WelcomeUI
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { Step.entries.size }
    val primaryButtonData = when (state.value.step) {
        Step.WELCOME -> true to stringResource(R.string.next)
        Step.AGREEMENT -> state.value.isAgreementAccepted to stringResource(R.string.next)
        Step.STATES -> (state.value.examState != null) to stringResource(R.string.get_started)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                OnBoardingEvent.Navigate -> {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
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

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = false,
            key = { Step.entries[it] }
        ) { page ->
            when (page) {
                0 -> WelcomeUI()
                1 -> AgreementUI(
                    isAgreementAccepted = state.value.isAgreementAccepted,
                    onAgreementClick = viewModel::onAgreementClick
                )
                2 -> StatesUI(
                    examState = state.value.examState,
                    onRadioClick = viewModel::onRadioClick
                )
            }
        }

        NavigationProgress(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            size = pagerState.pageCount,
            currentIndex = pagerState.currentPage
        )

        PrimaryButton(
            onClick = viewModel::onNextClick,
            modifier = Modifier.padding(
                start = Dimens.M_0,
                end = Dimens.M_0,
                bottom = Dimens.L_0
            ),
            enabled = primaryButtonData.first,
            text = primaryButtonData.second
        )
    }
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