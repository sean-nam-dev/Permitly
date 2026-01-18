package com.sean.permitly.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
            .height(Dimens.xxxl),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(widthDp = 200)
@Composable
private fun PrimaryButtonComponentPreview() {
    PermitlyTheme {
        PrimaryButton(
            onClick = {},
            enabled = true,
            text = "Next"
        )
    }
}