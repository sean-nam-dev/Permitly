package com.sean.permitly.presentation.onboarding

interface OnBoardingAction {
    fun onNextClick()

    fun onAgreementClick(value: Boolean)
}