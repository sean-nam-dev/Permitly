package com.sean.permitly

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
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
    fun `initial step is welcome`() {
        val viewModel = OnboardingViewModel(SavedStateHandle())
        assertEquals(Step.WELCOME, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick triggers Navigate event`() = runTest {
        val viewModel = OnboardingViewModel(SavedStateHandle())
        viewModel.event.test {
            viewModel.onNextClick()
            assertEquals(OnboardingEvent.Navigate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isAgreementAccepted is false by default`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.AGREEMENT
                )
            )
        )
        assertFalse(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onAgreementClick changes isAgreementAccepted from false to true`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.AGREEMENT
                )
            )
        )
        viewModel.onAgreementClick()
        assertTrue(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onNextClick does not change step if isAgreementAccepted is false`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.AGREEMENT
                )
            )
        )
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick does not trigger Navigate event`() = runTest {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.AGREEMENT
                )
            )
        )
        viewModel.event.test {
            viewModel.onNextClick()
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `examState is NONE by default`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.STATES
                )
            )
        )
        assertTrue(viewModel.state.value.examState == State.NONE)
    }

    @Test
    fun `onRadioClick assigns value to examState`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.STATES
                )
            )
        )
        viewModel.onRadioClick(State.NJ)
        assertEquals(State.NJ, viewModel.state.value.examState)
    }

    @Test
    fun `onNextClick does not trigger navigation if currentState is NONE`() = runTest {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.STATES
                )
            )
        )
        viewModel.event.test {
            viewModel.onNextClick()
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onNextClick triggers navigation if currentState is not NONE`() = runTest {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.STEP to Step.STATES,
                    OnboardingViewModel.STATE to State.NJ
                )
            )
        )
        viewModel.event.test {
            viewModel.onNextClick()
            assertEquals(OnboardingEvent.Navigate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}