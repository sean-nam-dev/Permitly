package com.sean.permitly.presentation.onboarding.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sean.permitly.R

enum class State {
    NONE,
    NJ,
    NY
}

@Composable
fun State.displayName() = when (this) {
    State.NONE -> stringResource(R.string.none)
    State.NJ -> stringResource(R.string.new_jersey)
    State.NY -> stringResource(R.string.new_york)
}