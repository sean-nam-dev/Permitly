package com.sean.permitly

import androidx.lifecycle.SavedStateHandle
import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.presentation.onboarding.util.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import app.cash.turbine.test
import com.sean.permitly.presentation.onboarding.OnBoardingEvent
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial step is welcome`() {
        val viewModel = OnBoardingViewModel(SavedStateHandle())
        assertEquals(Step.WELCOME, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick changes step from welcome to agreement`() {
        val viewModel = OnBoardingViewModel(SavedStateHandle())
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick triggers Navigate event`() = runTest {
        val viewModel = OnBoardingViewModel(SavedStateHandle())
        viewModel.event.test {
            viewModel.onNextClick()
            assertEquals(OnBoardingEvent.Navigate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isAgreementAccepted is false by default`() {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.AGREEMENT.key))
        )
        assertFalse(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onAgreementClick changes isAgreementAccepted from false to true`() {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.AGREEMENT.key))
        )
        viewModel.onAgreementClick()
        assertTrue(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onNextClick does not change step if isAgreementAccepted is false`() {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.AGREEMENT.key))
        )
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick does not trigger Navigate event`() = runTest {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.AGREEMENT.key))
        )
        viewModel.event.test {
            viewModel.onNextClick()
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onNextClick changes step from agreement to states`() = runTest {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(
                mapOf(
                    "step" to Step.AGREEMENT.key,
                    "agreement" to true
                )
            )
        )
        viewModel.event.test {
            viewModel.onNextClick()
            assertEquals(Step.STATES, viewModel.state.value.step)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `examState is NONE by default`() {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.STATES.key))
        )
        assertTrue(viewModel.state.value.examState == State.NONE)
    }

    @Test
    fun `onRadioClick assigns value to examState`() {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.STATES.key))
        )
        viewModel.onRadioClick(State.NJ)
        assertEquals(State.NJ, viewModel.state.value.examState)
    }

    @Test
    fun `onNextClick does not trigger navigation if currentState is NONE`() = runTest {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(mapOf("step" to Step.STATES.key))
        )
        viewModel.event.test {
            viewModel.onNextClick()
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onNextClick triggers navigation if currentState is not NONE`() = runTest {
        val viewModel = OnBoardingViewModel(
            SavedStateHandle(
                mapOf(
                    "step" to Step.STATES.key,
                    "state" to State.NJ.key
                )
            )
        )
        viewModel.event.test {
            viewModel.onNextClick()
            assertEquals(OnBoardingEvent.Navigate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}