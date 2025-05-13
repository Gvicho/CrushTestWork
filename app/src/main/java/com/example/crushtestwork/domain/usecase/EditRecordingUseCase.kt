package com.example.crushtestwork.domain.usecase

import com.example.crushtestwork.domain.client.RecordingsClient
import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError
import com.example.crushtestwork.domain.model.RecordingItem

class EditRecordingUseCase(
    private val recordingsClient: RecordingsClient
) {
    suspend operator fun invoke(
        editedRecording: RecordingItem
    ): ResultFace<Unit, NetworkError> {
        return recordingsClient.editRecording(editedRecording)
    }
}