package com.sean.permitly

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sean.permitly.presentation.onboarding.Step
import com.sean.permitly.presentation.onboarding.welcome.WelcomeTags
import com.sean.permitly.presentation.onboarding.welcome.WelcomeUI
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickNextChangesStep() {
        var step by mutableStateOf(Step.WELCOME)

        composeTestRule.setContent {
            WelcomeUI(
                onNextClick = {
                    step = Step.AGREEMENT
                }
            )
        }

        composeTestRule
            .onNodeWithTag(WelcomeTags.NEXT_BUTTON)
            .performClick()

        assertEquals(Step.AGREEMENT, step)
    }
}