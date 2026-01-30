package com.sean.permitly.presentation.onboarding

import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step

data class OnboardingState(
    val step: Step,
    val isAgreementAccepted: Boolean,
    val examState: State
)