package com.sean.permitly.presentation.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    minFontSize: TextUnit = 16.sp,
    style: TextStyle = LocalTextStyle.current
) {
    var fontSize by remember { mutableStateOf(style.fontSize) }

    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Visible,
        style = style,
        onTextLayout = { result ->
            if (result.hasVisualOverflow && fontSize > minFontSize) {
                fontSize = (fontSize.value * 0.9f).coerceAtLeast(minFontSize.value).sp
            }
        }
    )
}