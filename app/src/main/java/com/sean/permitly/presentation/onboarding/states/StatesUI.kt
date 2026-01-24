package com.sean.permitly.presentation.onboarding.states

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import com.sean.permitly.presentation.component.NavigationProgress
import com.sean.permitly.presentation.component.PrimaryButton
import com.sean.permitly.presentation.component.TextedRadioButton
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.presentation.onboarding.util.displayName
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.Elevation
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun StatesUI(
    examState: State?,
    onRadioClick: (State) -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.xl),
        verticalArrangement = Arrangement.spacedBy(Dimens.xl)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.choose_state),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.6f)
                .shadow(
                    elevation = Elevation.standard,
                    shape = RoundedCornerShape(Dimens.md)
                )
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(Dimens.md)
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.lg)
        ) {
            LazyColumn(
                modifier = Modifier.testTag(StatesTags.STATE_LAZY_COLUMN)
                    .weight(1f)
            ) {
                items(
                    items = State.entries,
                    key = { it }
                ) {
                    TextedRadioButton(
                        modifier = Modifier.testTag(StatesTags.RADIO_BUTTON + it),
                        text = it.displayName(),
                        selected = it == examState,
                        onClick = {
                            onRadioClick(it)
                        }
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = Dimens.lg),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Text(
                modifier = Modifier.padding(
                    start = Dimens.lg,
                    end = Dimens.lg,
                    bottom = Dimens.lg
                ).align(Alignment.CenterHorizontally),
                text = stringResource(R.string.you_can_change),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationProgress(
                size = Step.entries.size,
                currentIndex = Step.entries.indexOf(Step.STATES)
            )
            PrimaryButton(
                onClick = onNextClick,
                modifier = Modifier.testTag(StatesTags.GET_STARTED_BUTTON)
                    .padding(bottom = Dimens.xl),
                enabled = examState != null,
                text = stringResource(R.string.get_started)
            )
        }
    }
}

@Preview
@Composable
private fun StatesUIPreview() {
    var examState by remember { mutableStateOf<State?>(null) }
    val onRadioClick: (State) -> Unit = {
        examState = it
    }

    PermitlyTheme {
        StatesUI(
            examState = examState,
            onRadioClick = onRadioClick,
            onNextClick = {}
        )
    }
}