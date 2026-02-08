package com.sean.permitly.presentation.onboarding.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sean.permitly.R

enum class State(val id: Int) {
    NONE(R.string.none),
    NJ(R.string.new_jersey),
    NY(R.string.new_york);
}

@Composable
fun State.displayName(): String = stringResource(id)