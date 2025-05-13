package com.example.crushtestwork.presentation.features.add_recording

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenEffect
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenEvent
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenState
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.addRecordingScreen(
    onGoBack: () -> Unit
) = composable<AddRecordingsScreen> {
    val viewModel = koinViewModel<AddRecordingScreenViewModel>()
    AddRecordingsScreen(
        state = viewModel.viewState,
        sendEvent = viewModel::obtainEvent
    )
    LaunchedEffect(viewModel.effects) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AddRecordingScreenEffect.GoBack -> onGoBack()
            }
        }
    }
}

@Serializable
data object AddRecordingsScreen

@Composable
fun AddRecordingsScreen(
    state: AddRecordingScreenState,
    sendEvent: (AddRecordingScreenEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    TextField(
                        onValueChange = {
                            sendEvent(AddRecordingScreenEvent.OnTitleInput(it))
                        },
                        value = state.title,
                        label = {
                            Text("title")
                        }
                    )
                }
                item {
                    TextField(
                        onValueChange = {
                            sendEvent(AddRecordingScreenEvent.OnDescriptionInput(it))
                        },
                        value = state.description,
                        label = {
                            Text("description")
                        }
                    )
                }
                state.isError?.let {
                    item {
                        Text(text = it, color = Color.Red)
                    }
                }
                item {
                    Button(
                        onClick = {
                            sendEvent(AddRecordingScreenEvent.OnCreteClicked)
                        }
                    ) {
                        Text("ADD")
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}