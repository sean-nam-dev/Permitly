package com.sean.permitly.presentation.onboarding.pages.states

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.R
import com.sean.permitly.presentation.component.TextedRadioButton
import com.sean.permitly.presentation.onboarding.util.OnboardingTags
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.displayName
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.Elevation
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun StatesPage(statesPageData: StatesPageData) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(horizontal = Dimens.M_0),
            verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.M_0),
                text = stringResource(R.string.choose_state),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = Elevation.standard,
                        shape = RoundedCornerShape(Dimens.S_0)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(Dimens.S_0)
                    ),
                verticalArrangement = Arrangement.spacedBy(Dimens.M_0),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .testTag(OnboardingTags.States.LAZY_COLUMN)
                        .weight(1f),
                    contentPadding = PaddingValues(top = Dimens.S_0)
                ) {
                    items(
                        items = State.entries,
                        key = { it }
                    ) {
                        if (it != State.NONE) {
                            TextedRadioButton(
                                modifier = Modifier.testTag(OnboardingTags.States.RADIO_BUTTON + it),
                                text = it.displayName(),
                                selected = it == statesPageData.examState,
                                onClick = {
                                    statesPageData.onRadioClick(it)
                                }
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = Dimens.M_0),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Text(
                    modifier = Modifier.padding(bottom = Dimens.M_0),
                    text = stringResource(R.string.you_can_change),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun StatesPagePreview() {
    var examState by remember { mutableStateOf<State>(State.NONE) }
    val onRadioClick: (State) -> Unit = {
        examState = it
    }

    PermitlyTheme {
        StatesPage(
            statesPageData = StatesPageData(
                examState = examState,
                onRadioClick = onRadioClick
            )
        )
    }
}