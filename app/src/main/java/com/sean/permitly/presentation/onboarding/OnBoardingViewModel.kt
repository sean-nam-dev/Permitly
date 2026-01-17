package com.sean.permitly.presentation.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class OnBoardingViewModel : ViewModel() {
    val _state = MutableStateFlow(OnBoardingState(Step.WELCOME))
    val state = _state

    fun onNextClick() {
        _state.value = OnBoardingState(Step.AGREEMENT)
    }
}

class OnBoardingState(
    val step: Step
)

enum class Step {
    WELCOME,
    AGREEMENT,
    STATES
}