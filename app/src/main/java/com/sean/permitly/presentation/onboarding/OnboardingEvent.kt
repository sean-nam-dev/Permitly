package com.sean.permitly.presentation.onboarding

sealed interface OnboardingEvent {
    data object Next : OnboardingEvent
    data object Navigate : OnboardingEvent
}