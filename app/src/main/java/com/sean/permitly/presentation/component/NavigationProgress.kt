package com.sean.permitly.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.ui.theme.Dimens
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun NavigationProgress(
    modifier: Modifier = Modifier,
    size: Int,
    currentIndex: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.S_2)
    ) {
        repeat(size) { index ->
            val animatedColor = animateColorAsState(
                if (index == currentIndex) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )

            Box(
                modifier = Modifier.size(Dimens.S_2)
                    .background(
                        color = animatedColor.value,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationProgressPreview() {
    PermitlyTheme {
        NavigationProgress(
            size = 3,
            currentIndex = 0
        )
    }
}