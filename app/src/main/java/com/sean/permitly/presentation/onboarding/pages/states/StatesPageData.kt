package com.sean.permitly.presentation.onboarding.pages.states

import com.sean.permitly.core.State

data class StatesPageData(
    val examState: State,
    val onRadioClick: (State) -> Unit
)
