package com.sean.permitly.presentation.onboarding.util

import com.sean.permitly.domain.model.State

object OnboardingTags {
    const val NAVIGATION_BUTTON = "NAVIGATION_BUTTON"

    object Welcome {
        const val PAGE = "WELCOME_PAGE"
    }

    object Agreement {
        const val PAGE = "AGREEMENT_PAGE"
        const val CHECKBOX = "AGREEMENT_CHECKBOX"
    }
    object States {
        const val PAGE = "STATES_PAGE"
        const val LAZY_COLUMN = "STATES_LAZY_COLUMN"

        fun radioButton(state: State): String =
            "STATES_RADIO_BUTTON_$state"
    }
}