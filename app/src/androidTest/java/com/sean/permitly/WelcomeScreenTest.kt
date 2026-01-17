package com.sean.permitly

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest {
    private val viewModel = OnBoardingViewModel()
    val composeTestRule = createComposeRule()

    @Test
    fun clickNextChangesStep() {
        composeTestRule.setContent {
            WelcomeScreen()
        }

        composeTestRule
            .onNodeWithTag("NEXT_BUTTON")
            .performClick()

        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }
}