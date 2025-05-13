package com.example.crushtestwork.presentation.features.recordings.contract

import com.example.crushtestwork.domain.model.RecordingItem

// this is possible actions coming from user
sealed interface RecordingsScreenEvent {
    data object OnRefresh : RecordingsScreenEvent
    data object OnAddRecordingClicked : RecordingsScreenEvent
    data class OnModifyRecordingClicked(val recording: RecordingItem) : RecordingsScreenEvent
    data class OnDeleteRecordingClicked(val id: String) : RecordingsScreenEvent
    data object OnDismissModifyDialog : RecordingsScreenEvent
    data object OnSaveRecordingModification : RecordingsScreenEvent
    data class OnDescriptionInput(val text: String) : RecordingsScreenEvent
    data class OnTitleInput(val text: String) : RecordingsScreenEvent
}