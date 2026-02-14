package com.sean.permitly

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sean.permitly.core.Key
import com.sean.permitly.presentation.onboarding.OnboardingScreen
import com.sean.permitly.presentation.onboarding.OnboardingViewModel
import com.sean.permitly.presentation.onboarding.util.OnboardingTags
import com.sean.permitly.core.State
import com.sean.permitly.presentation.onboarding.util.Step
import org.junit.Assert.assertTrue
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
    fun navigation_from_welcome_to_agreement() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(SavedStateHandle()),
                navigateToLogin = {}
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.Welcome.PAGE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsEnabled()
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.Agreement.PAGE)
            .assertIsDisplayed()
    }

    @Test
    fun agreement_checkbox_toggles_navigation_availability() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(
                    SavedStateHandle(
                        mapOf(
                            Key.STEP.name to Step.AGREEMENT,
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

    @Test
    fun from_agreement_to_states() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(
                    SavedStateHandle(
                        mapOf(
                            Key.STEP.name to Step.AGREEMENT,
                            Key.AGREEMENT.name to true
                        )
                    )
                ),
                navigateToLogin = {}
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.Agreement.PAGE)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsEnabled()
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.States.PAGE)
            .assertIsDisplayed()
    }

    @Test
    fun selecting_state_enables_navigation() {
        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(
                    SavedStateHandle(
                        mapOf(
                            Key.STEP.name to Step.STATES,
                            Key.AGREEMENT.name to true
                        )
                    )
                ),
                navigateToLogin = {}
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsNotEnabled()

        composeTestRule.onNodeWithTag(OnboardingTags.States.LAZY_COLUMN)
            .performScrollToKey(State.NJ)

        composeTestRule.onNodeWithTag(OnboardingTags.States.RADIO_BUTTON + State.NJ)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun navigation_to_login_trigger_check() {
        var isTriggered = false

        composeTestRule.setContent {
            OnboardingScreen(
                viewModel = OnboardingViewModel(
                    SavedStateHandle(
                        mapOf(
                            Key.STEP.name to Step.STATES,
                            Key.AGREEMENT.name to true,
                            Key.STATE.name to State.NJ
                        )
                    )
                ),
                navigateToLogin = { isTriggered = true }
            )
        }

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        assertTrue(isTriggered)
    }
}