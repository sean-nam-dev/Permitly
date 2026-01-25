package com.sean.permitly.presentation.onboarding.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.R
import com.sean.permitly.ui.theme.Elevation
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun WelcomeUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.M_0)
    ) {
        Image(
            painter = painterResource(R.drawable.welcome_poster),
            contentDescription = stringResource(R.string.welcome_poster),
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.8f)
                .shadow(
                    elevation = Elevation.standard,
                    shape = RoundedCornerShape(
                        bottomStart = Dimens.L_0,
                        bottomEnd = Dimens.L_0
                    )
                )
                .clip(
                    RoundedCornerShape(
                        bottomStart = Dimens.L_0,
                        bottomEnd = Dimens.L_0
                    )
                ),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(Dimens.M_0)
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
                modifier = Modifier.padding(top = Dimens.S_0),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeUIPreview() {
    PermitlyTheme {
        WelcomeUI()
    }
}