package com.sean.permitly

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sean.permitly.presentation.onboarding.OnboardingScreen
import com.sean.permitly.presentation.onboarding.OnboardingViewModel
import com.sean.permitly.presentation.onboarding.util.OnboardingTags
import com.sean.permitly.presentation.onboarding.util.State
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onboarding_happy_path() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(SavedStateHandle()),
                navigateToLogin = { },
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.Welcome.PAGE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.Agreement.PAGE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(OnboardingTags.Agreement.CHECKBOX)
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.States.PAGE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(OnboardingTags.States.LAZY_COLUMN)
            .performScrollToKey(State.NJ)

        composeTestRule.onNodeWithTag(OnboardingTags.States.RADIO_BUTTON + State.NJ)
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsEnabled()
    }
}