package com.sean.permitly

import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.presentation.onboarding.util.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial step is welcome`() {
        val viewModel = provideViewModel(Step.WELCOME)
        assertEquals(Step.WELCOME, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick changes step from welcome to agreement`() {
        val viewModel = provideViewModel(Step.WELCOME)
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `isAgreementAccepted is false`() {
        val viewModel = provideViewModel(Step.AGREEMENT)
        assertFalse(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onAgreementClick changes isAgreementAccepted from false to true`() {
        val viewModel = provideViewModel(Step.AGREEMENT)
        viewModel.onAgreementClick()
        assertTrue(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onNextClick does not change step if isAgreementAccepted is false`() {
        val viewModel = provideViewModel(Step.AGREEMENT)
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick changes step from agreement to states`() {
        val viewModel = provideViewModel(Step.AGREEMENT)
        viewModel.onAgreementClick()
        viewModel.onNextClick()
        assertEquals(Step.STATES, viewModel.state.value.step)
    }

    @Test
    fun `examState is null by default`() {
        val viewModel = provideViewModel(Step.STATES)
        assertTrue(viewModel.state.value.examState == null)
    }

    @Test
    fun `onRadioClick assigns value to examState`() {
        val viewModel = provideViewModel(Step.STATES)
        viewModel.onRadioClick(State.NJ)
        assertEquals(State.NJ, viewModel.state.value.examState)
    }

//    @Test
//    fun `onNextClick does not change step if examState is null`() {
//        val viewModel = OnBoardingViewModel()
//        viewModel.onNextClick()
//        viewModel.onAgreementClick()
//        viewModel.onNextClick()
//        assertEquals(Step.STATES, viewModel.state.value.step)
//    }

//    @Test
//    fun `onNextClick changes step if examState is not null`() {
//        val viewModel = OnBoardingViewModel()
//        viewModel.onNextClick()
//        viewModel.onAgreementClick()
//        viewModel.onNextClick()
//        viewModel.onRadioClick(State.NJ)
//        viewModel.onNextClick()
//        assertEquals(Step.LOGIN, viewModel.state.value.step)
//    }

    private fun provideViewModel(step: Step): OnBoardingViewModel {
        return when (step) {
            Step.WELCOME -> { OnBoardingViewModel() }
            Step.AGREEMENT -> {
                OnBoardingViewModel().apply {
                    onNextClick()
                }
            }
            Step.STATES -> {
                OnBoardingViewModel().apply {
                    onNextClick()
                    onAgreementClick()
                    onNextClick()
                }
            }
        }
    }
}