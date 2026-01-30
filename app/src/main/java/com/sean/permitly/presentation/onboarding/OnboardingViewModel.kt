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
            step = Step.fromKey(
                savedStateHandle["step"] ?: Step.WELCOME.key
            ),
            isAgreementAccepted = savedStateHandle["agreement"] ?: false,
            examState = State.fromKey(
                savedStateHandle["state"] ?: State.NONE.key
            )
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
        when (_state.value.step) {
            Step.WELCOME -> {
                _state.update { it.copy(step = Step.AGREEMENT) }
            }
            Step.AGREEMENT -> {
                _state.update { it.copy(step = Step.STATES) }
            }
            Step.STATES -> {

            }
        }
        viewModelScope.launch {
            _event.emit(OnboardingEvent.Navigate)
        }
        update()
    }

    override fun onAgreementClick() {
        _state.update { it.copy(isAgreementAccepted = !it.isAgreementAccepted) }
        update()
    }

    override fun onRadioClick(state: State) {
        _state.update { it.copy(examState = state) }
        update()
    }

    private fun update() {
        savedStateHandle["step"] = _state.value.step.key
        savedStateHandle["agreement"] = _state.value.isAgreementAccepted
        savedStateHandle["state"] = _state.value.examState.key
    }
}