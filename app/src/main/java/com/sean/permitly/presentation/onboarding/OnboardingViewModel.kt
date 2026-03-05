package com.sean.permitly.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sean.permitly.domain.model.State
import com.sean.permitly.domain.usecase.WriteFirstLaunchUseCase
import com.sean.permitly.domain.usecase.WriteStateUseCase
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
    private val writeStateUseCase: WriteStateUseCase,
    private val writeFirstLaunchUseCase: WriteFirstLaunchUseCase
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

            Step.AGREEMENT -> if (_state.value.isAgreementAccepted) {
                updateStep(Step.STATES)
                emitNext()
            }

            Step.STATES -> if (_state.value.examState != State.NONE) {
                viewModelScope.launch {
                    writeStateUseCase(_state.value.examState)
                    writeFirstLaunchUseCase(_state.value.isAgreementAccepted)

                    _event.emit(OnboardingEvent.Navigate)
                }
            }
        }
    }

    override fun onAgreementClick() {
        val newValue = !_state.value.isAgreementAccepted
        _state.update { it.copy(isAgreementAccepted = newValue) }
        savedStateHandle[AGREEMENT_KEY] = newValue
    }

    override fun onRadioClick(state: State) {
        _state.update { it.copy(examState = state) }
        savedStateHandle[STATE_KEY] = state
    }

    private fun updateStep(step: Step) {
        _state.update { it.copy(step = step) }
        savedStateHandle[STEP_KEY] = step
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