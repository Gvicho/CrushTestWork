package com.example.crushtestwork.presentation.features.add_recording

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Add Recording!",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(400),
                        fontSize = 28.sp,
                        color = Color.Green
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
                item {
                    TextField( modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                        .fillMaxWidth(),
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
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 20.dp)
                            .fillMaxWidth(),
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
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        onClick = {
                            sendEvent(AddRecordingScreenEvent.OnCreteClicked)
                        },
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        if (state.isLoading) {
                            LinearProgressIndicator()
                        } else {
                            Text("ADD")
                        }
                    }
                }
            }
        }
    }
}