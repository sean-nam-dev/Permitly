package com.sean.permitly

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sean.permitly.presentation.onboarding.util.Step
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StatesTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun getStartedButtonIsDisabled() {
        composeTestRule.setContent {
            StatesUI(
                stateList = State.entries,
                examState = null,
                onRadioClick = {},
                onNextClick = {}
            )
        }

        composeTestRule.onNodeWithTag(StatesTags.GET_STARTED_BUTTON)
            .assertIsNotEnabled()
    }

    @Test
    fun getStartedButtonIsEnabled() {
        composeTestRule.setContent {
            StatesUI(
                stateList = State.entries,
                examState = State.NJ,
                onRadioClick = {},
                onNextClick = {}
            )
        }

        composeTestRule.onNodeWithTag(StatesTags.GET_STARTED_BUTTON)
            .assertIsEnabled()
    }

    @Test
    fun onRadioClickChangesExamStates() {
        var currentExamState by mutableStateOf<State?>(null)
        val onCurrentExamState: (State) -> Unit = {
            currentExamState = it
        }

        composeTestRule.setContent {
            StatesUI(
                stateList = State.entries,
                examState = currentExamState,
                onRadioClick = {
                    onCurrentExamState(State.NJ)
                },
                onNextClick = {}
            )
        }

        composeTestRule.onNodeWithTag(StatesTags.RADIO_BUTTON + State.NJ)
            .performClick()

        assertTrue(currentExamState != null)
    }

    @Test
    fun onGetStartedChangesStep() {
        var step by mutableStateOf(Step.STATES)
        val onStepChange: () -> Unit = {
            step = Step.LOGIN
        }

        var currentExamState by mutableStateOf<State?>(null)
        val onCurrentExamStateChange: (State) -> Unit = {
            currentExamState = it
        }

        composeTestRule.setContent {
            StatesUI(
                stateList = State.entries,
                examState = currentExamState,
                onRadioClick = {
                    onCurrentExamStateChange(State.NJ)
                },
                onNextClick = onStepChange
            )
        }

        composeTestRule.onNodeWithTag(StatesTags.STATE_LAZY_COLUMN)
            .performScrollToKey(State.NJ)

        composeTestRule.onNodeWithTag(StatesTags.RADIO_BUTTON + State.NJ)
            .performClick()

        composeTestRule.onNodeWithTag(StatesTags.GET_STARTED_BUTTON)
            .performClick()

        assertEquals(Step.LOGIN, step)
    }
}