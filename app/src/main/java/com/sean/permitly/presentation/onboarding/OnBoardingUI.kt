package com.sean.permitly.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.R
import com.sean.permitly.presentation.component.NavigationProgress
import com.sean.permitly.presentation.component.PrimaryButton
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPage
import com.sean.permitly.presentation.onboarding.pages.states.StatesPage
import com.sean.permitly.presentation.onboarding.pages.WelcomePage
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.OnBoardingTags
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun OnBoardingUI(
    pagerState: PagerState,
    step: Step,
    agreementPageData: AgreementPageData,
    statesPageData: StatesPageData,
    onNextClick: () -> Unit
) {
    val primaryButtonData = when (step) {
        Step.WELCOME -> true to stringResource(R.string.next)
        Step.AGREEMENT -> agreementPageData.isAgreementAccepted to stringResource(R.string.next)
        Step.STATES -> (statesPageData.examState != null) to stringResource(R.string.get_started)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = false,
            key = { Step.entries[it] }
        ) { page ->
            when (page) {
                0 -> WelcomePage()
                1 -> AgreementPage(agreementPageData)
                2 -> StatesPage(statesPageData)
            }
        }

        NavigationProgress(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            size = pagerState.pageCount,
            currentIndex = pagerState.currentPage
        )

        PrimaryButton(
            onClick = onNextClick,
            modifier = Modifier.testTag(OnBoardingTags.NAVIGATION_BUTTON)
                .padding(
                    start = Dimens.M_0,
                    end = Dimens.M_0,
                    bottom = Dimens.L_0
                ),
            enabled = primaryButtonData.first,
            text = primaryButtonData.second
        )
    }
}

@Preview
@Composable
private fun OnBoardingUIPreview() {
    val pagerState = rememberPagerState { Step.entries.size }

    PermitlyTheme {
        OnBoardingUI(
            pagerState = pagerState,
            step = Step.WELCOME,
            agreementPageData = AgreementPageData(
                isAgreementAccepted = false,
                onAgreementClick = {}
            ),
            statesPageData = StatesPageData(
                examState = null,
                onRadioClick = {}
            ),
            onNextClick = {}
        )
    }
}