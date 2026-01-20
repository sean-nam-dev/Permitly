package com.sean.permitly.presentation.onboarding

data class OnBoardingState(
    val step: Step = Step.WELCOME,
    val isAgreementAccepted: Boolean = false
)