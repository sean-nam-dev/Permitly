package com.sean.permitly.presentation.onboarding.agreement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.R
import com.sean.permitly.presentation.component.NavigationProgress
import com.sean.permitly.presentation.component.PrimaryButton
import com.sean.permitly.presentation.onboarding.util.Step
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.Elevation
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun AgreementUI(
    isAgreementAccepted: Boolean,
    onAgreementClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val agreementTitles = stringArrayResource(R.array.agreement_titles)
    val agreementTexts = stringArrayResource(R.array.agreement_texts)

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.xl),
        verticalArrangement = Arrangement.spacedBy(Dimens.xl)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.privacy_and_terms),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyColumn(
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
            contentPadding = PaddingValues(Dimens.lg),
            verticalArrangement = Arrangement.spacedBy(Dimens.lg)
        ) {
            itemsIndexed(agreementTitles) { index, title ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.sm),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = agreementTexts[index],
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Row(
            modifier = Modifier.testTag(AgreementTags.AGREEMENT_CHECKBOX)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.xs))
                .clickable(
                    onClick = {
                        onAgreementClick()
                    }
                ),
            horizontalArrangement = Arrangement.spacedBy(Dimens.sm)
        ) {
            Checkbox(
                checked = isAgreementAccepted,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.onBackground,
                    uncheckedColor = MaterialTheme.colorScheme.onBackground,
                    checkmarkColor = MaterialTheme.colorScheme.background
                )
            )
            Text(
                text = stringResource(R.string.i_understand_this_app),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationProgress(
                size = Step.entries.size,
                currentIndex = Step.entries.indexOf(Step.AGREEMENT)
            )
            PrimaryButton(
                onClick = onNextClick,
                modifier = Modifier.testTag(AgreementTags.NEXT_BUTTON)
                    .padding(bottom = Dimens.xl),
                enabled = isAgreementAccepted,
                text = stringResource(R.string.next)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementUIPreview() {
    var isAgreementAccepted by remember {
        mutableStateOf(false)
    }
    val onAgreementClick: () -> Unit = {
        isAgreementAccepted = !isAgreementAccepted
    }

    PermitlyTheme {
        AgreementUI(
            isAgreementAccepted = isAgreementAccepted,
            onAgreementClick = onAgreementClick,
            onNextClick = {  }
        )
    }
}