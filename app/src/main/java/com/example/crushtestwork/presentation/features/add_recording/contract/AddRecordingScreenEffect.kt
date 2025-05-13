package com.example.crushtestwork.presentation.features.add_recording.contract

sealed interface AddRecordingScreenEffect {
    data object GoBack : AddRecordingScreenEffect
}