package com.example.crushtestwork.presentation.common.di

import com.example.crushtestwork.presentation.features.add_recording.AddRecordingScreenViewModel
import com.example.crushtestwork.presentation.features.recordings.RecordingsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RecordingsScreenViewModel)
    viewModelOf(::AddRecordingScreenViewModel)
}