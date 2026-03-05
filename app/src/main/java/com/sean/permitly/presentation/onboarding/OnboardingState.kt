package com.sean.permitly.presentation.onboarding

import com.sean.permitly.domain.model.State
import com.sean.permitly.presentation.onboarding.util.Step

data class OnboardingState(
    val step: Step,
    val isAgreementAccepted: Boolean,
    val examState: State
) {
    val isPrimaryButtonEnabled: Boolean
        get() = when (step) {
            Step.WELCOME -> true
            Step.AGREEMENT -> isAgreementAccepted
            Step.STATES -> isAgreementAccepted && examState != State.NONE
        }
}