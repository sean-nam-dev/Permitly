package com.sean.permitly.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.presentation.util.getErrorMessage

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(state.value.step.index) {
        Step.entries.size
    }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is OnboardingEvent.Next -> {
                    pagerState.animateScrollToPage(
                        page = state.value.step.index,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
                is OnboardingEvent.Navigate -> {
                    navigateToLogin()
                }
                is OnboardingEvent.ShowLocalErrorToast -> {
                    snackBarHostState.showSnackbar(
                        message = context.getErrorMessage(event.error),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    OnboardingUI(
        pagerState = pagerState,
        snackBarHostState = snackBarHostState,
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