package com.sean.permitly.presentation.onboarding

import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step

data class OnboardingState(
    val step: Step = Step.WELCOME,
    val isAgreementAccepted: Boolean = false,
    val examState: State = State.NONE
)