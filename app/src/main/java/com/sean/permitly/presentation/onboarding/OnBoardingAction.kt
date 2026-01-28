package com.sean.permitly.presentation.onboarding

import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step

interface OnBoardingAction {
    fun onNextClick()
    fun onAgreementClick()
    fun onRadioClick(state: State)
    fun changeStep(step: Step)
}