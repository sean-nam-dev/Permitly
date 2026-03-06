package com.sean.permitly.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.R
import com.sean.permitly.domain.model.State
import com.sean.permitly.presentation.component.NavigationProgress
import com.sean.permitly.presentation.component.PrimaryButton
import com.sean.permitly.presentation.onboarding.pages.WelcomePage
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPage
import com.sean.permitly.presentation.onboarding.pages.agreement.AgreementPageData
import com.sean.permitly.presentation.onboarding.pages.states.StatesPage
import com.sean.permitly.presentation.onboarding.pages.states.StatesPageData
import com.sean.permitly.presentation.onboarding.util.OnboardingTags
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun OnboardingUI(
    pagerState: PagerState,
    snackBarHostState: SnackbarHostState,
    agreementPageData: AgreementPageData,
    statesPageData: StatesPageData,
    isPrimaryButtonEnabled: Boolean,
    onPrimaryButtonClick: () -> Unit
) {
    val primaryButtonText = when (pagerState.currentPage) {
        Step.WELCOME.index -> stringResource(R.string.next)
        Step.AGREEMENT.index -> stringResource(R.string.next)
        else -> stringResource(R.string.get_started)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
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
                modifier = Modifier
                    .testTag(OnboardingTags.NAVIGATION_BUTTON)
                    .padding(
                        start = Dimens.M_0,
                        end = Dimens.M_0,
                        bottom = Dimens.L_0
                    ),
                text = primaryButtonText,
                enabled = isPrimaryButtonEnabled,
                action = onPrimaryButtonClick
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingUIPreview() {
    PermitlyTheme {
        OnboardingUI(
            pagerState = rememberPagerState {
                Step.entries.size
            },
            snackBarHostState = remember {
                SnackbarHostState()
            },
            agreementPageData = AgreementPageData(
                isAgreementAccepted = true,
                onAgreementClick = {}
            ),
            statesPageData = StatesPageData(
                examState = State.NJ,
                onRadioClick = {}
            ),
            isPrimaryButtonEnabled = true,
            onPrimaryButtonClick = {}
        )
    }
}