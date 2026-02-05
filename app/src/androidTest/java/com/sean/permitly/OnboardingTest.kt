package com.sean.permitly

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
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
import com.sean.permitly.presentation.onboarding.util.Step
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

    @Test
    fun default_onboarding_state_displays_welcome_step() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(SavedStateHandle()),
                navigateToLogin = {}
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.Welcome.PAGE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertHasClickAction()
    }

    @Test
    fun agreement_acceptance_toggles_navigation_availability() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(
                    SavedStateHandle(
                        mapOf(
                            OnboardingViewModel.STEP to Step.AGREEMENT,
                            OnboardingViewModel.AGREEMENT to false,
                            OnboardingViewModel.STATE to State.NONE
                        )
                    )
                ),
                navigateToLogin = {}
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsNotEnabled()

        composeTestRule.onNodeWithTag(OnboardingTags.Agreement.CHECKBOX)
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsEnabled()

        composeTestRule.onNodeWithTag(OnboardingTags.Agreement.CHECKBOX)
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsNotEnabled()
    }
}