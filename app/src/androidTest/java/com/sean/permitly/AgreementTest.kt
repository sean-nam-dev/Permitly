//package com.sean.permitly
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.test.assertIsEnabled
//import androidx.compose.ui.test.assertIsNotEnabled
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.test.performClick
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.sean.permitly.presentation.onboarding.util.Step
//import com.sean.permitly.presentation.onboarding.agreement.AgreementTags
//import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPage
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertTrue
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class AgreementTest {
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun nextButtonIsDisabled() {
//        composeTestRule.setContent {
//            AgreementPage(
//                isAgreementAccepted = false,
//                onAgreementClick = { }
//            )
//        }
//
//        composeTestRule.onNodeWithTag(AgreementTags.NEXT_BUTTON)
//            .assertIsNotEnabled()
//    }
//
//    @Test
//    fun nextButtonIsEnabled() {
//        composeTestRule.setContent {
//            AgreementPage(
//                isAgreementAccepted = true,
//                onAgreementClick = { }
//            )
//        }
//
//        composeTestRule.onNodeWithTag(AgreementTags.NEXT_BUTTON)
//            .assertIsEnabled()
//    }
//
//    @Test
//    fun agreementCheckboxStateChanges() {
//        var isAgreementAccepted by mutableStateOf(false)
//        val onAgreementClick: () -> Unit = {
//            isAgreementAccepted = !isAgreementAccepted
//        }
//
//        composeTestRule.setContent {
//            AgreementPage(
//                isAgreementAccepted = isAgreementAccepted,
//                onAgreementClick = onAgreementClick
//            )
//        }
//
//        composeTestRule.onNodeWithTag(AgreementTags.AGREEMENT_CHECKBOX)
//            .performClick()
//
//        assertTrue(isAgreementAccepted)
//
//        composeTestRule.onNodeWithTag(AgreementTags.AGREEMENT_CHECKBOX)
//            .performClick()
//
//        assertFalse(isAgreementAccepted)
//    }
//
//    @Test
//    fun nextButtonChangesStep() {
//        var step by mutableStateOf(Step.AGREEMENT)
//
//        composeTestRule.setContent {
//            AgreementPage(
//                isAgreementAccepted = true,
//                onAgreementClick = { }
//            )
//        }
//
//        composeTestRule.onNodeWithTag(AgreementTags.NEXT_BUTTON)
//            .performClick()
//
//        assertEquals(Step.STATES, step)
//    }
//}