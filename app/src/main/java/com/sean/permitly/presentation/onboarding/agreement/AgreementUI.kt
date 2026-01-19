package com.sean.permitly.presentation.onboarding.agreement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun AgreementUI(
    isAgreementAccepted: Boolean,
    onAgreementClick: (Boolean) -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                modifier = Modifier.testTag(AgreementTags.AGREEMENT_CHECKBOX),
                checked = isAgreementAccepted,
                onCheckedChange = {
                    onAgreementClick(it)
                }
            )
            Text("Check")
        }
        Button(
            enabled = isAgreementAccepted,
            modifier = Modifier.testTag(AgreementTags.NEXT_BUTTON),
            onClick = onNextClick
        ) {
            Text("Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementUIPreview() {
    var isAgreementAccepted by mutableStateOf(false)
    val onAgreementClick: (Boolean) -> Unit = {
        isAgreementAccepted = it
    }

    PermitlyTheme {
        AgreementUI(
            isAgreementAccepted = isAgreementAccepted,
            onAgreementClick = onAgreementClick,
            onNextClick = {  }
        )
    }
}