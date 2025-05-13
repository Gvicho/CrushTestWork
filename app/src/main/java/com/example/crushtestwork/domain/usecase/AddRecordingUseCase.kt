package com.example.crushtestwork.domain.usecase

import com.example.crushtestwork.domain.client.RecordingsClient
import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError
import com.example.crushtestwork.domain.model.RecordingItem

class AddRecordingUseCase(
    private val recordingsClient: RecordingsClient
) {
    suspend operator fun invoke(
        newRecording: RecordingItem
    ): ResultFace<Unit, NetworkError> {
        return recordingsClient.addRecording(newRecording)
    }
}