package com.sean.permitly

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sean.permitly.core.Key
import com.sean.permitly.dispatcher.MainDispatcherRule
import com.sean.permitly.presentation.onboarding.OnboardingEvent
import com.sean.permitly.presentation.onboarding.OnboardingViewModel
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `onNextClick triggers Navigate event`() = runTest {
        val viewModel = OnboardingViewModel(SavedStateHandle())

        assertEquals(Step.WELCOME, viewModel.state.value.step)

        viewModel.event.test {
            viewModel.onNextClick()
            assertEquals(OnboardingEvent.Navigate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAgreementClick toggles isAgreementAccepted`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    Key.STEP.name to Step.AGREEMENT
                )
            )
        )

        assertFalse(viewModel.state.value.isAgreementAccepted)

        viewModel.onAgreementClick()

        assertTrue(viewModel.state.value.isAgreementAccepted)

        viewModel.onAgreementClick()

        assertFalse(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onRadioClick assigns value to examState`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    Key.STEP.name to Step.STATES
                )
            )
        )

        assertEquals(State.NONE, viewModel.state.value.examState)

        viewModel.onRadioClick(State.NJ)

        assertEquals(State.NJ, viewModel.state.value.examState)
    }
}