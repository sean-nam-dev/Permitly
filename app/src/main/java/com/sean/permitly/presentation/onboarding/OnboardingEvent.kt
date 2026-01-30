package com.sean.permitly.presentation.onboarding

sealed interface OnboardingEvent {
    data object Navigate : OnboardingEvent
}