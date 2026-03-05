package com.sean.permitly.presentation.onboarding

import com.sean.permitly.domain.error.DataError

sealed interface OnboardingEvent {
    data object Next : OnboardingEvent
    data object Navigate : OnboardingEvent
    data class ShowLocalErrorToast(val error: DataError.Local) : OnboardingEvent
}