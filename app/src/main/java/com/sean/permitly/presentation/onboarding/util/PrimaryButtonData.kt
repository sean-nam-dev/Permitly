package com.sean.permitly.presentation.onboarding.util

data class PrimaryButtonData(
    val text: String,
    val enabled: Boolean,
    val action: () -> Unit
)
