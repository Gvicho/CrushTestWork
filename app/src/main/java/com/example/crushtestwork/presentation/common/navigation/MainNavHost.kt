package com.example.crushtestwork.presentation.common.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.crushtestwork.presentation.common.navigation.animations.enterTransition
import com.example.crushtestwork.presentation.common.navigation.animations.exitTransition
import com.example.crushtestwork.presentation.common.navigation.animations.popEnterTransition
import com.example.crushtestwork.presentation.common.navigation.animations.popExitTransition
import com.example.crushtestwork.presentation.features.add_recording.AddRecordingsScreen
import com.example.crushtestwork.presentation.features.add_recording.addRecordingScreen
import com.example.crushtestwork.presentation.features.recordings.RecordingsScreen
import com.example.crushtestwork.presentation.features.recordings.recordingsScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RecordingsScreen,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {

            recordingsScreen(
                onOpenNewRecordingScreen = {
                    navController.navigate(AddRecordingsScreen)
                }
            )

            addRecordingScreen(
                onGoBack = {
                    navController.popBackStack()
                }
            )

        }
    }
}