package com.sean.permitly.presentation.onboarding.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sean.permitly.R

enum class State {
    NONE,
    NJ,
    NY,
    TX
}

@Composable
fun State.displayName(): String {
    val resId = when (this) {
        State.NONE -> R.string.none
        State.NJ -> R.string.new_jersey
        State.NY -> R.string.new_york
        State.TX -> R.string.texas
    }
    return stringResource(resId)
}