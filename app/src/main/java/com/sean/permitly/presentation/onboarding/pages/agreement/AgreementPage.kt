package com.sean.permitly.presentation.onboarding.pages.agreement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.R
import com.sean.permitly.presentation.component.TextedCheckbox
import com.sean.permitly.presentation.onboarding.util.OnboardingTags
import com.sean.permitly.ui.theme.Elevation
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun AgreementPage(agreementPageData: AgreementPageData) {
    val agreementTitles = stringArrayResource(R.array.agreement_titles)
    val agreementTexts = stringArrayResource(R.array.agreement_texts)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.M_0),
                text = stringResource(R.string.privacy_and_terms),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = Dimens.M_0)
                    .weight(1f)
                    .shadow(
                        elevation = Elevation.standard,
                        shape = RoundedCornerShape(Dimens.S_0)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(Dimens.S_0)
                    ),
                contentPadding = PaddingValues(Dimens.M_0),
                verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
            ) {
                itemsIndexed(agreementTitles) { index, title ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimens.S_0),
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
        }

        TextedCheckbox(
            modifier = Modifier.testTag(OnboardingTags.Agreement.CHECKBOX),
            text = stringResource(R.string.i_understand),
            checked = agreementPageData.isAgreementAccepted,
            onClick = agreementPageData.onAgreementClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementPagePreview() {
    var isAgreementAccepted by remember {
        mutableStateOf(false)
    }
    val onAgreementClick: () -> Unit = {
        isAgreementAccepted = !isAgreementAccepted
    }

    PermitlyTheme {
        AgreementPage(
            AgreementPageData(
                isAgreementAccepted = isAgreementAccepted,
                onAgreementClick = onAgreementClick
            )
        )
    }
}