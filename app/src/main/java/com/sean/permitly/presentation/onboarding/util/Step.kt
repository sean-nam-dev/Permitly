package com.sean.permitly.presentation.onboarding.util

enum class Step(val key: String) {
    WELCOME("welcome"),
    AGREEMENT("agreement"),
    STATES("states");

    companion object {
        fun fromKey(key: String): Step =
            entries.first { it.key == key }
    }
}