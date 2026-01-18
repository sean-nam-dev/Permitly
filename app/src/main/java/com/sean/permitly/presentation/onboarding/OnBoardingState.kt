package com.sean.permitly.presentation.onboarding

import com.sean.permitly.presentation.onboarding.Step

data class OnBoardingState(
    val step: Step = Step.WELCOME
)