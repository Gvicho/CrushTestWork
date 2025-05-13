package com.example.crushtestwork.presentation.features.add_recording.contract

sealed interface AddRecordingScreenEvent {
    data class OnTitleInput(val text: String) : AddRecordingScreenEvent
    data class OnDescriptionInput(val text: String) : AddRecordingScreenEvent
    data object OnCreteClicked : AddRecordingScreenEvent
}