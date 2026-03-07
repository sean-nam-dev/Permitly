package com.sean.permitly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sean.permitly.navigation.AuthGraph
import com.sean.permitly.navigation.NavGraph
import com.sean.permitly.ui.theme.PermitlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermitlyTheme {
                NavGraph(
                    startDestination = AuthGraph
                )
            }
        }
    }
}