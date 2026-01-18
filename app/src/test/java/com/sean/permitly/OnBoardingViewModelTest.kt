package com.sean.permitly

import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.Step
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    private lateinit var viewModel: OnBoardingViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = OnBoardingViewModel()
    }

    @Test
    fun `initial screen is welcome`() {
        assertEquals(Step.WELCOME, viewModel.state.value.step)
    }

    @Test
    fun `click next changes step from welcome to agreement`() = runTest {
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel.state.value.step)
        advanceUntilIdle()
    }
}