package com.sean.permitly.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sean.permitly.domain.model.State
import com.sean.permitly.domain.repository.AppSettingsRepository
import com.sean.permitly.domain.usecase.WriteFirstLaunchUseCase
import com.sean.permitly.domain.usecase.WriteStateUseCase
import com.sean.permitly.presentation.onboarding.OnboardingEvent
import com.sean.permitly.presentation.onboarding.OnboardingViewModel
import com.sean.permitly.presentation.onboarding.OnboardingViewModel.Companion.STEP_KEY
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.util.dispatcher.MainDispatcherRule
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    private val appSettingsRepository: AppSettingsRepository = FakeAppSettingsRepository()
    private val writeStateUseCase: WriteStateUseCase = WriteStateUseCase(appSettingsRepository)
    private val writeFirstLaunchUseCase: WriteFirstLaunchUseCase =
        WriteFirstLaunchUseCase(appSettingsRepository)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `onNextClick triggers Next event`() = runTest {
        val viewModel = OnboardingViewModel(
            savedStateHandle = SavedStateHandle(),
            writeStateUseCase = writeStateUseCase,
            writeFirstLaunchUseCase = writeFirstLaunchUseCase
        )

        Assert.assertEquals(Step.WELCOME, viewModel.state.value.step)

        viewModel.event.test {
            viewModel.onNextClick()
            Assert.assertEquals(OnboardingEvent.Next, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAgreementClick toggles isAgreementAccepted`() {
        val viewModel = OnboardingViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    STEP_KEY to Step.AGREEMENT
                )
            ),
            writeStateUseCase = writeStateUseCase,
            writeFirstLaunchUseCase = writeFirstLaunchUseCase
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
            savedStateHandle = SavedStateHandle(
                mapOf(
                    STEP_KEY to Step.STATES
                )
            ),
            writeStateUseCase = writeStateUseCase,
            writeFirstLaunchUseCase = writeFirstLaunchUseCase
        )

        Assert.assertEquals(State.NONE, viewModel.state.value.examState)

        viewModel.onRadioClick(State.NJ)

        Assert.assertEquals(State.NJ, viewModel.state.value.examState)
    }
}