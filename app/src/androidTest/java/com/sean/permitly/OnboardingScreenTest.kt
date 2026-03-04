package com.sean.permitly

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sean.permitly.domain.model.State
import com.sean.permitly.presentation.onboarding.OnboardingUI
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.OnboardingTags
import com.sean.permitly.presentation.onboarding.util.Step
import kotlinx.coroutines.launch
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onboarding_happy_path() {
        composeTestRule.setOnboardingContent()

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

        composeTestRule.onNodeWithTag(OnboardingTags.States.radioButton(State.NJ))
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsEnabled()
    }

    @Test
    fun navigation_from_welcome_to_agreement() {
        composeTestRule.setOnboardingContent()

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
        composeTestRule.setOnboardingContent(step = Step.AGREEMENT)

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
        composeTestRule.setOnboardingContent(
            step = Step.AGREEMENT,
            isAgreementAccepted = true
        )

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
        composeTestRule.setOnboardingContent(
            step = Step.STATES,
            isAgreementAccepted = true
        )

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsNotEnabled()

        composeTestRule.onNodeWithTag(OnboardingTags.States.LAZY_COLUMN)
            .performScrollToKey(State.NJ)

        composeTestRule.onNodeWithTag(OnboardingTags.States.radioButton(State.NJ))
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun navigate_to_login() {
        var isTriggered = false

        composeTestRule.setOnboardingContent(
            step = Step.STATES,
            isAgreementAccepted = true,
            examState = State.NJ,
            onNavigateClick = {
                isTriggered = true
            }
        )

        composeTestRule.onNodeWithTag(OnboardingTags.NAVIGATION_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        assertTrue(isTriggered)
    }

    private fun ComposeContentTestRule.setOnboardingContent(
        step: Step = Step.WELCOME,
        isAgreementAccepted: Boolean = false,
        examState: State = State.NONE,
        onNavigateClick: () -> Unit = {}
    ) {
        setContent {
            val pagerState = rememberPagerState(initialPage = step.index) { Step.entries.size }

            var step by remember { mutableStateOf(step) }
            var isAgreementAccepted by remember { mutableStateOf(isAgreementAccepted) }
            var examState by remember { mutableStateOf(examState) }

            val scope = rememberCoroutineScope()

            OnboardingUI(
                pagerState = pagerState,
                step = step,
                agreementPageData = AgreementPageData(
                    isAgreementAccepted = isAgreementAccepted,
                    onAgreementClick = { isAgreementAccepted = !isAgreementAccepted }
                ),
                statesPageData = StatesPageData(
                    examState = examState,
                    onRadioClick = { examState = it }
                ),
                onNextClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = step.index
                        )
                    }
                },
                onNavigateClick = onNavigateClick,
                onStepChange = {
                    step = Step.entries[step.index + 1]
                }
            )
        }
    }
}