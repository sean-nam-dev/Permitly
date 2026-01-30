package com.sean.permitly.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), OnboardingAction {
    private val _state = MutableStateFlow(
        OnboardingState(
            step = savedStateHandle[STEP] ?: Step.WELCOME,
            isAgreementAccepted = savedStateHandle[AGREEMENT] ?: false,
            examState = savedStateHandle[STATE] ?: State.NONE
        )
    )
    val state: StateFlow<OnboardingState>
        get() = _state

    private val _event = MutableSharedFlow<OnboardingEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val event: SharedFlow<OnboardingEvent>
        get() = _event

    override fun onNextClick() {
        viewModelScope.launch {
            _event.emit(OnboardingEvent.Navigate)
        }
    }

    override fun onStepChange(step: Step) {
        _state.update { it.copy(step = step) }
        savedStateHandle[STEP] = _state.value.step
    }

    override fun onAgreementClick() {
        _state.update { it.copy(isAgreementAccepted = !it.isAgreementAccepted) }
        savedStateHandle[AGREEMENT] = _state.value.isAgreementAccepted
    }

    override fun onRadioClick(state: State) {
        _state.update { it.copy(examState = state) }
        savedStateHandle[STATE] = _state.value.examState
    }

    companion object {
        const val STEP = "step"
        const val STATE = "state"
        const val AGREEMENT = "agreement"
    }
}