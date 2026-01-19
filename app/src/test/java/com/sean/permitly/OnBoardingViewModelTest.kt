package com.sean.permitly

import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.Step
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial screen is welcome`() {
        val viewModel = OnBoardingViewModel()
        assertEquals(Step.WELCOME, viewModel.state.value.step)
    }

    @Test
    fun `click next changes step from welcome to agreement`() = runTest {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `agreement checkbox is false`() {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        assertTrue(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `click agreement checkbox changes isAgreementAccepted value from false to true`() {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        viewModel.onAgreementClick()
        assertTrue(viewModel.state.value.isAgreementAccepted)
    }

    @Test
    fun `next click does nothing if agreement is not accepted`() = runTest {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
    }

    @Test
    fun `click next changes step from agreement to states`() = runTest {
        val viewModel = OnBoardingViewModel()
        viewModel.onNextClick()
        viewModel.onAgreementClick()
        viewModel.onNextClick()
        assertEquals(Step.STATES, viewModel.state.value.step)
    }
}