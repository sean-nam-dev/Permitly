package com.sean.permitly.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sean.permitly.domain.model.State
import com.sean.permitly.domain.repository.AppSettingsRepository
import com.sean.permitly.domain.usecase.CompleteOnboardingUseCase
import com.sean.permitly.domain.usecase.WriteAgreementUseCase
import com.sean.permitly.domain.usecase.WriteStateUseCase
import com.sean.permitly.presentation.onboarding.OnboardingEvent
import com.sean.permitly.presentation.onboarding.OnboardingViewModel
import com.sean.permitly.presentation.onboarding.OnboardingViewModel.Companion.AGREEMENT_KEY
import com.sean.permitly.presentation.onboarding.OnboardingViewModel.Companion.STATE_KEY
import com.sean.permitly.presentation.onboarding.OnboardingViewModel.Companion.STEP_KEY
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.util.dispatcher.MainDispatcherRule
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse


class OnboardingViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val appSettingsRepository: AppSettingsRepository = FakeAppSettingsRepository()
    private val writeStateUseCase: WriteStateUseCase = WriteStateUseCase(appSettingsRepository)
    private val writeAgreementUseCase: WriteAgreementUseCase =
        WriteAgreementUseCase(appSettingsRepository)
    private val completeOnboardingUseCase: CompleteOnboardingUseCase = CompleteOnboardingUseCase(
        writeStateUseCase = writeStateUseCase,
        writeAgreementUseCase = writeAgreementUseCase
    )

    @Test
    fun `initial state is correct when created with default params`() {
        val viewModel = createViewModel()
        val state = viewModel.state.value

        assertEquals(Step.WELCOME, state.step)
        assertFalse(state.isAgreementAccepted)
        assertEquals(State.NONE, state.examState)
    }

    @Test
    fun `onPrimaryButtonClick on WELCOME step navigates to AGREEMENT and emits Next`() = runTest {
        val viewModel = createViewModel()

        viewModel.event.test {
            viewModel.onPrimaryButtonClick()

            assertEquals(OnboardingEvent.Next, awaitItem())
            assertEquals(Step.AGREEMENT, viewModel.state.value.step)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onPrimaryButtonClick on AGREEMENT step when agreement accepted navigates to STATES and emits Next`() =
        runTest {
            val viewModel = createViewModel(
                step = Step.AGREEMENT,
                isAgreementAccepted = true
        )

            viewModel.event.test {
                viewModel.onPrimaryButtonClick()

                assertEquals(OnboardingEvent.Next, awaitItem())
                assertEquals(Step.STATES, viewModel.state.value.step)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onPrimaryButtonClick on AGREEMENT step when agreement not accepted does nothing`() =
        runTest {
            val viewModel = createViewModel(step = Step.AGREEMENT)

        viewModel.event.test {
            viewModel.onPrimaryButtonClick()

            assertEquals(viewModel.state.value.step, Step.AGREEMENT)
            expectNoEvents()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAgreementClick toggles isAgreementAccepted`() {
        val viewModel = createViewModel(step = Step.AGREEMENT)

        viewModel.onAgreementClick()

        assertEquals(viewModel.state.value.isAgreementAccepted, true)

        viewModel.onAgreementClick()

        assertEquals(viewModel.state.value.isAgreementAccepted, false)
    }

    @Test
    fun `onPrimaryButtonClick on STATES step on success emits Navigate`() = runTest {
        val viewModel = createViewModel(
            step = Step.STATES,
            isAgreementAccepted = true,
            examState = State.NJ
        )

        viewModel.event.test {
            viewModel.onPrimaryButtonClick()

            assertEquals(OnboardingEvent.Navigate, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRadioClick updates examState`() {
        val viewModel = createViewModel(
            step = Step.STATES,
            isAgreementAccepted = true
        )

        viewModel.onRadioClick(State.NJ)

        assertEquals(viewModel.state.value.examState, State.NJ)

        viewModel.onRadioClick(State.NY)

        assertEquals(viewModel.state.value.examState, State.NY)
    }

    @Test
    fun `onPrimaryButtonClick on STATES step when state is NONE does nothing`() = runTest {
        val viewModel = createViewModel(
            step = Step.STATES,
            isAgreementAccepted = true
        )

        viewModel.event.test {
            viewModel.onPrimaryButtonClick()

            expectNoEvents()
        }
    }

    private fun createViewModel(
        step: Step = Step.WELCOME,
        isAgreementAccepted: Boolean = false,
        examState: State = State.NONE
    ): OnboardingViewModel = OnboardingViewModel(
        savedStateHandle = SavedStateHandle(
            mapOf(
                STEP_KEY to step,
                AGREEMENT_KEY to isAgreementAccepted,
                STATE_KEY to examState
            )
        ),
        completeOnboardingUseCase = completeOnboardingUseCase
    )
}