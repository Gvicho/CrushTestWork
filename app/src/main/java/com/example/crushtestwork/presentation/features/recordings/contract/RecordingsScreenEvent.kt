package com.example.crushtestwork.presentation.features.recordings.contract

// this is possible actions coming from user
sealed interface RecordingsScreenEvent {
    data object OnRefresh : RecordingsScreenEvent
    data object OnAddRecordingClicked : RecordingsScreenEvent
    data class OnModifyRecordingClicked(val id: String) : RecordingsScreenEvent
}