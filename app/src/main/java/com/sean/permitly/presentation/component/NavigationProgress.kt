package com.sean.permitly.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        horizontalArrangement = Arrangement.spacedBy(Dimens.S_0)
    ) {
        repeat(size) { index ->
            val isCurrent = index == currentIndex
            val animatedColor = animateColorAsState(
                if (isCurrent) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            val animatedWidth = animateDpAsState(
                if (isCurrent) Dimens.L_0
                else Dimens.S_2
            )
            val animatedShape = animateDpAsState(
                if (isCurrent) Dimens.M_0
                else Dimens.S_2
            )

            Box(
                modifier = Modifier
                    .size(
                        height = Dimens.S_2,
                        width = animatedWidth.value
                    )
                    .background(
                        color = animatedColor.value,
                        shape = RoundedCornerShape(animatedShape.value)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationProgressPreview() {
    var currentIndex by remember { mutableIntStateOf(0) }
    val onCurrentIndexChange: () -> Unit = {
        if (currentIndex == 2) {
            currentIndex = 0
        } else {
            currentIndex++
        }
    }

    PermitlyTheme {
        Column(
            modifier = Modifier.size(150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(5.dp)) {
                NavigationProgress(
                    size = 3,
                    currentIndex = currentIndex
                )
            }

            Button(
                onClick = onCurrentIndexChange
            ) {
                Text("Next")
            }
        }
    }
}