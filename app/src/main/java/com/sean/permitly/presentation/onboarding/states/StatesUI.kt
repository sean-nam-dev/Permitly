package com.sean.permitly.presentation.onboarding.states

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.sean.permitly.presentation.onboarding.util.State
import com.sean.permitly.presentation.onboarding.util.displayName
import com.sean.permitly.ui.theme.PermitlyTheme

@Composable
fun StatesUI(
    examState: State?,
    onRadioClick: (State) -> Unit,
    onNextClick: () -> Unit
) {
    Column() {
        LazyColumn(modifier = Modifier.testTag(StatesTags.STATE_LAZY_COLUMN)) {
            items(State.entries, key = { it }) { state ->
                Row() {
                    RadioButton(
                        modifier = Modifier.testTag(StatesTags.RADIO_BUTTON + state),
                        selected = state == examState,
                        onClick = {
                            onRadioClick(state)
                        }
                    )
                    Text(state.displayName())
                }
            }
        }
        Button(
            modifier = Modifier.testTag(StatesTags.GET_STARTED_BUTTON),
            enabled = examState != null,
            onClick = onNextClick
        ) {
            Text("Get Started")
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