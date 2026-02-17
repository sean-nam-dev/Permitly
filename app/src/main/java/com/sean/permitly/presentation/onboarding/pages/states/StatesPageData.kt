package com.sean.permitly.presentation.onboarding.pages.states

import com.sean.permitly.presentation.onboarding.util.State

data class StatesPageData(
    val examState: State,
    val onRadioClick: (State) -> Unit
)
