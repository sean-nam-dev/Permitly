package com.sean.permitly.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sean.permitly.R
import com.sean.permitly.domain.model.State

@Composable
fun State.displayName() = stringResource(
    id = when (this) {
        State.NONE -> R.string.none
        State.NJ -> R.string.new_jersey
        State.NY -> R.string.new_york
    }
)