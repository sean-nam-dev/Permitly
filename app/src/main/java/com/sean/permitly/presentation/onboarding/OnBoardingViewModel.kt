package com.sean.permitly.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel : ViewModel(), OnBoardingAction {
    private val _state = MutableStateFlow(OnBoardingState())
    val state: StateFlow<OnBoardingState>
        get() = _state

    private val _event = MutableSharedFlow<OnBoardingEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val event: SharedFlow<OnBoardingEvent>
        get() = _event

    override fun onNextClick() {
        when (_state.value.step) {
            Step.WELCOME -> {
                _state.update { it.copy(step = Step.AGREEMENT) }
                viewModelScope.launch {
                    _event.emit(OnBoardingEvent.Navigate)
                }
            }
            Step.AGREEMENT -> {
                if (_state.value.isAgreementAccepted) {
                    _state.update { it.copy(step = Step.STATES) }
                    viewModelScope.launch {
                        _event.emit(OnBoardingEvent.Navigate)
                    }
                }
            }
            Step.STATES -> {
                if (_state.value.examState != null) {
                    viewModelScope.launch {
                        _event.emit(OnBoardingEvent.Navigate)
                    }
                }
            }
        }
    }

    override fun onAgreementClick() {
        _state.update { it.copy(isAgreementAccepted = !it.isAgreementAccepted) }
    }

    override fun onRadioClick(state: State) {
        _state.update { it.copy(examState = state) }
    }
}