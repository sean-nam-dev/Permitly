package com.sean.permitly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sean.permitly.core.edgeToEdgeNoStatusBar
import com.sean.permitly.navigation.AuthGraph
import com.sean.permitly.navigation.NavGraph
import com.sean.permitly.ui.theme.PermitlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edgeToEdgeNoStatusBar()
        setContent {
            PermitlyTheme {
                NavGraph(
                    startDestination = AuthGraph
                )
            }
        }
    }
}