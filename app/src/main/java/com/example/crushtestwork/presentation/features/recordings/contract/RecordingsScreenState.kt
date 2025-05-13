package com.example.crushtestwork.presentation.features.recordings.contract

import com.example.crushtestwork.domain.model.RecordingItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

// viewmodel saves this state. in android there is configuration change (differ from ios :) )
// during that everything is destroyed , but not viewmodel
// so if this is saved in view model it will survive and clients work on screen wont be lost
data class RecordingsScreenState(
    val isLoading: Boolean = false,
    val listOfRecordings: PersistentList<RecordingItem> = persistentListOf(),
    val recordingToEdit: RecordingItem? = null,
    val isDialogLoading: Boolean = false
)