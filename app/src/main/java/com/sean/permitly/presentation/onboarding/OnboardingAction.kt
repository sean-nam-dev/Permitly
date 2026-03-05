package com.sean.permitly.presentation.onboarding

import com.sean.permitly.domain.model.State

interface OnboardingAction {
    fun onPrimaryButtonClick()
    fun onAgreementClick()
    fun onRadioClick(state: State)
}