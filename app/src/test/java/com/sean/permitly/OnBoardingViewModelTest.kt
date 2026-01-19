package com.sean.permitly

import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.Step
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial step is welcome`() {
        val viewModel = OnBoardingViewModel()
        assertEquals(Step.WELCOME, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick changes step from welcome to agreement`() = runTest {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `isAgreementAccepted is false`() {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        assertFalse(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onAgreementClick changes isAgreementAccepted from false to true`() {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        viewModel.onAgreementClick(true)
        assertTrue(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `onNextClick does not change step if isAgreementAccepted is false`() = runTest {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `onNextClick changes step from agreement to states`() = runTest {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        viewModel.onAgreementClick(true)
        viewModel.onNextClick()
        assertEquals(Step.STATES, viewModel.state.value.step)
    }
}