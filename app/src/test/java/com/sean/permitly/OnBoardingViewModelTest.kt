package com.sean.permitly

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
    fun `initial screen is welcome`() {
        assertEquals(Step.WELCOME, viewModel._state.value.step)
    }

    @Test
    fun `click next changes step from welcome to agreement`() {
        viewModel.onNextClick()
        assertEquals(Step.AGREEMENT, viewModel._state.value.step)
    }
}