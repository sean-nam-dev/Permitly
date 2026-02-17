package com.sean.permitly.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sean.permitly.presentation.onboarding.OnboardingEvent
import com.sean.permitly.presentation.onboarding.OnboardingViewModel
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.util.dispatcher.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `onNextClick triggers Navigate event`() = runTest {
        val viewModel = OnboardingViewModel(SavedStateHandle())

        Assert.assertEquals(Step.WELCOME, viewModel.state.value.step)

        viewModel.event.test {
            viewModel.onNextClick()
            Assert.assertEquals(OnboardingEvent.Navigate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAgreementClick toggles isAgreementAccepted`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.Companion.STEP_KEY to Step.AGREEMENT
                )
            )
        )

        Assert.assertFalse(viewModel.state.value.isAgreementAccepted)

        viewModel.onAgreementClick()

        Assert.assertTrue(viewModel.state.value.isAgreementAccepted)

        viewModel.onAgreementClick()

        Assert.assertFalse(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onRadioClick assigns value to examState`() {
        val viewModel = OnboardingViewModel(
            SavedStateHandle(
                mapOf(
                    OnboardingViewModel.Companion.STEP_KEY to Step.STATES
                )
            )
        )

        Assert.assertEquals(State.NONE, viewModel.state.value.examState)

        viewModel.onRadioClick(State.NJ)

        Assert.assertEquals(State.NJ, viewModel.state.value.examState)
    }
}