package com.sean.permitly.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sean.permitly.presentation.onboarding.util.PrimaryButtonData
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    primaryButtonData: PrimaryButtonData
) {
    Button(
        onClick = primaryButtonData.action,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.XXL_5),
        enabled = primaryButtonData.enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    ) {
        Text(
            text = primaryButtonData.text,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 200
)
@Composable
private fun PrimaryButtonComponentPreview() {
    val primaryButtonData1 = PrimaryButtonData(
        text = "Next",
        enabled = true,
        action = {}
    )
    val primaryButtonData2 = PrimaryButtonData(
        text = "Next",
        enabled = false,
        action = {}
    )

    PermitlyTheme {
        Column {
            PrimaryButton(
                primaryButtonData = primaryButtonData1
            )
            Spacer(modifier = Modifier.height(10.dp))
            PrimaryButton(
                primaryButtonData = primaryButtonData2
            )
        }
    }
}