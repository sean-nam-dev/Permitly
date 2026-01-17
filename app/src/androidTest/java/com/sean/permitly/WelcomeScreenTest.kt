package com.sean.permitly

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.Step
import com.sean.permitly.presentation.onboarding.WelcomeScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest {
    private val viewModel = OnBoardingViewModel()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickNextChangesStep() {
        composeTestRule.setContent {
            WelcomeScreen(viewModel)
        }

        composeTestRule
            .onNodeWithTag("NEXT_BUTTON")
            .performClick()

        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }
}