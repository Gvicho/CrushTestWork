package com.example.crushtestwork.presentation.features.add_recording.contract

data class AddRecordingScreenState(
    val title: String,
    val description: String,
    val isLoading: Boolean = false,
    val isError: String? = null
)