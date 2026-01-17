package com.sean.permitly

import com.sean.permitly.presentation.onboarding.OnBoardingViewModel
import com.sean.permitly.presentation.onboarding.Step
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OnBoardingViewModelTest {

    private lateinit var viewModel: OnBoardingViewModel

    @Before
    fun setup() {
        viewModel = OnBoardingViewModel()
    }

    @Test
    fun initial_screen_is_welcome() {
        assertEquals(Step.WELCOME, viewModel._state.value.step)
    }

    @Test
    fun click_next_changes_step_from_welcome_to_agreement() {
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel._state.value.step)
    }
}