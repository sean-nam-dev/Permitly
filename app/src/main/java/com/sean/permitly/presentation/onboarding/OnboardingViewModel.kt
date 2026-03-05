package com.sean.permitly.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sean.permitly.domain.error.onError
import com.sean.permitly.domain.error.onSuccess
import com.sean.permitly.domain.model.State
import com.sean.permitly.domain.usecase.CompleteOnboardingUseCase
import com.sean.permitly.presentation.onboarding.util.Step
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel(), OnboardingAction {
    private val _state = MutableStateFlow(
        OnboardingState(
            step = savedStateHandle[STEP_KEY] ?: Step.WELCOME,
            isAgreementAccepted = savedStateHandle[AGREEMENT_KEY] ?: false,
            examState = savedStateHandle[STATE_KEY] ?: State.NONE
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

    override fun onPrimaryButtonClick() {
        when (_state.value.step) {
            Step.WELCOME -> {
                updateStep(Step.AGREEMENT)
                emitNext()
            }
            Step.AGREEMENT -> if (_state.value.isPrimaryButtonEnabled) {
                updateStep(Step.STATES)
                emitNext()
            }
            Step.STATES -> if (_state.value.isPrimaryButtonEnabled) {
                viewModelScope.launch {
                    completeOnboardingUseCase(
                        state = _state.value.examState,
                        isAgreementAccepted = _state.value.isAgreementAccepted
                    ).onSuccess {
                        _event.emit(OnboardingEvent.Navigate)
                    }.onError {
                        resetToDefault()
                        _event.emit(OnboardingEvent.ShowLocalErrorToast(it))
                    }
                }
            }
        }
    }

    override fun onAgreementClick() {
        updateAgreement(!_state.value.isAgreementAccepted)
    }

    override fun onRadioClick(state: State) {
        updateState(state)
    }

    private fun updateStep(new: Step) {
        _state.update { it.copy(step = new) }
        savedStateHandle[STEP_KEY] = new
    }

    private fun updateAgreement(new: Boolean) {
        _state.update { it.copy(isAgreementAccepted = new) }
        savedStateHandle[AGREEMENT_KEY] = new
    }

    private fun updateState(new: State) {
        _state.update { it.copy(examState = new) }
        savedStateHandle[STATE_KEY] = new
    }

    private fun resetToDefault() {
        updateStep(Step.WELCOME)
        updateAgreement(false)
        updateState(State.NONE)
    }

    private fun emitNext() {
        viewModelScope.launch {
            _event.emit(OnboardingEvent.Next)
        }
    }

    companion object {
        internal const val STEP_KEY = "step"
        internal const val AGREEMENT_KEY = "agreement"
        internal const val STATE_KEY = "state"
    }
}