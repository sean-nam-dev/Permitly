package com.sean.permitly.presentation.onboarding

import com.sean.permitly.presentation.onboarding.util.State

interface OnBoardingAction {
    fun onNextClick()
    fun onAgreementClick()
    fun onRadioClick(state: State)
}