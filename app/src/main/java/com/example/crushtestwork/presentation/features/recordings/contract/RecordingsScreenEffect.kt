package com.example.crushtestwork.presentation.features.recordings.contract

// this is some effect that viewmodel might throw back to screen (navigation action for example)
sealed interface RecordingsScreenEffect {
    data object OpenCreateNewRecordingScreen : RecordingsScreenEffect
}