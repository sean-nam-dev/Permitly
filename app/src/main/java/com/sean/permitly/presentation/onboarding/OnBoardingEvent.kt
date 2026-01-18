package com.sean.permitly.presentation.onboarding

sealed interface OnBoardingEvent {
    data object Navigate : OnBoardingEvent
    data object Navigat2 : OnBoardingEvent
}