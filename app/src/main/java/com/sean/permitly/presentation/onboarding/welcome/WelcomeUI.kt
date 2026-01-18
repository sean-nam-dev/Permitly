package com.sean.permitly.presentation.onboarding.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sean.permitly.R
import com.sean.permitly.presentation.component.NavigationProgress
import com.sean.permitly.presentation.component.PrimaryButton
import com.sean.permitly.presentation.onboarding.OnBoardingTags
import com.sean.permitly.presentation.onboarding.Step
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun WelcomeUI(
    step: Step,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(Dimens.xl)
    ) {
        Image(
            painter = painterResource(R.drawable.welcome_poster),
            contentDescription = stringResource(R.string.welcome_poster),
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(
                    RoundedCornerShape(
                        bottomStart = Dimens.xxxl,
                        bottomEnd = Dimens.xxxl
                    )
                ),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(Dimens.xl)
        ) {
            Text(
                text = stringResource(R.string.build_confidence),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.for_your_dmv_test),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.practice_exam_questions),
                modifier = Modifier.padding(top = Dimens.md),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.xl),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavigationProgress(
                    size = Step.entries.size,
                    currentIndex = Step.entries.indexOf(step)
                )
                PrimaryButton(
                    onClick = onNextClick,
                    modifier = Modifier.testTag(OnBoardingTags.NEXT_BUTTON)
                        .padding(bottom = Dimens.xl),
                    enabled = true,
                    text = stringResource(R.string.next)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeUIPreview() {
    PermitlyTheme {
        WelcomeUI(
            step = Step.WELCOME,
            onNextClick = {}
        )
    }
}