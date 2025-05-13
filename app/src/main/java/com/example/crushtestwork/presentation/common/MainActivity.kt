package com.example.crushtestwork.presentation.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.crushtestwork.presentation.common.navigation.MainNavHost
import com.example.crushtestwork.presentation.common.ui.theme.CrushTestWorkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrushTestWorkTheme {
                MainNavHost()
            }
        }
    }
}