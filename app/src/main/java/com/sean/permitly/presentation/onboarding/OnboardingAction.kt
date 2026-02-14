package com.sean.permitly.presentation.onboarding

import com.sean.permitly.core.State
import com.sean.permitly.presentation.onboarding.util.Step

interface OnboardingAction {
    fun onNextClick()
    fun onStepChange(step: Step)
    fun onAgreementClick()
    fun onRadioClick(state: State)
}