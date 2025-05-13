package com.example.crushtestwork.presentation.features.recordings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenEffect
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenEvent
import com.example.crushtestwork.presentation.features.recordings.contract.RecordingsScreenState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.recordingsScreen(
    onOpenNewRecordingScreen: () -> Unit,
    onModifyNewRecordingScreen: (id: String) -> Unit
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
                is RecordingsScreenEffect.OpenModifyRecordingScreen -> onModifyNewRecordingScreen(
                    effect.id
                )
            }
        }
    }
}

@Serializable
data object RecordingsScreen

@Composable
fun RecordingsScreen(
    state: RecordingsScreenState,
    sendEvent: (RecordingsScreenEvent) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

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
            ) {
                items(state.listOfRecordings) {
                    RecordingItem(
                        recordingItem = it,
                        onEditClicked = {
                            sendEvent(RecordingsScreenEvent.OnModifyRecordingClicked(it.id ?: ""))
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
    onEditClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(10.dp),
                color = Color.Cyan
            )
    ) {
        Column {
            Text(text = recordingItem.title)
            Text(text = recordingItem.content)
        }
        IconButton(
            onClick = onEditClicked,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_pen_32),
                contentDescription = "edit"
            )
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