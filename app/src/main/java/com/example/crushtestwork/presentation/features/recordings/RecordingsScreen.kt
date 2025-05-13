package com.example.crushtestwork.presentation.features.recordings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.crushtestwork.R
import com.example.crushtestwork.domain.model.RecordingItem
import com.example.crushtestwork.presentation.common.ui.theme.CrushTestWorkTheme
import com.example.crushtestwork.presentation.features.add_recording.contract.AddRecordingScreenEvent
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenEffect
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenEvent
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.recordingsScreen(
    onOpenNewRecordingScreen: () -> Unit,
) = composable<RecordingsScreen> {
    val viewModel = koinViewModel<RecordingsScreenViewModel>()
    RecordingsScreen(
        state = viewModel.viewState,
        sendEvent = viewModel::obtainEvent
    )
    LaunchedEffect(viewModel.effects) {
        viewModel.effects.collect { effect ->
            when (effect) {
                RecordingsScreenEffect.OpenCreateNewRecordingScreen -> onOpenNewRecordingScreen()
            }
        }
    }
}

@Serializable
data object RecordingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingsScreen(
    state: RecordingsScreenState,
    sendEvent: (RecordingsScreenEvent) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    state.recordingToEdit?.let {
        BasicAlertDialog(
            onDismissRequest = {
                sendEvent(RecordingsScreenEvent.OnDismissModifyDialog)
            }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.Cyan
                    )
            ) {
                TextField(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onValueChange = {
                        sendEvent(RecordingsScreenEvent.OnTitleInput(it))
                    },
                    value = it.title,
                    label = {
                        Text("title")
                    }
                )
                TextField(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onValueChange = {
                        sendEvent(RecordingsScreenEvent.OnDescriptionInput(it))
                    },
                    value = it.content,
                    label = {
                        Text("description")
                    }
                )
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        sendEvent(RecordingsScreenEvent.OnSaveRecordingModification)
                    }
                ) {
                    if (state.isDialogLoading) {
                        LinearProgressIndicator()
                    } else {
                        Text("SAVE")
                    }
                }
            }
        }
    }

    DisposableEffect (Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                sendEvent(RecordingsScreenEvent.OnRefresh)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    sendEvent(RecordingsScreenEvent.OnAddRecordingClicked)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = "add"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                items(state.listOfRecordings) {
                    RecordingItem(
                        recordingItem = it,
                        onEditClicked = {
                            sendEvent(RecordingsScreenEvent.OnModifyRecordingClicked(it))
                        },
                        onDeleteClicked = {
                            sendEvent(RecordingsScreenEvent.OnDeleteRecordingClicked(it.id ?: ""))
                        }
                    )
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

@Composable
private fun RecordingItem(
    recordingItem: RecordingItem,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .background(
                shape = RoundedCornerShape(10.dp),
                color = Color.Magenta
            )
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = recordingItem.title)
            Text(text = recordingItem.content)
        }
        Column {
            IconButton(
                onClick = onEditClicked,
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_pen_32),
                    contentDescription = "edit"
                )
            }
            IconButton(
                onClick = onDeleteClicked,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_trash_32),
                    contentDescription = "remove"
                )
            }
        }
    }
}

@Composable
@Preview
fun RecordingsScreenPreview() = CrushTestWorkTheme {
    RecordingsScreen(
        state = RecordingsScreenState(
            isLoading = false,
            listOfRecordings = persistentListOf()
        ), sendEvent = { }

    )
}