package com.example.crushtestwork.domain.usecase

import com.example.crushtestwork.domain.client.RecordingsClient
import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError

class DeleteRecordingUseCase(
    private val recordingsClient: RecordingsClient
) {
    suspend operator fun invoke(
        id: String
    ): ResultFace<Boolean, NetworkError> {
        return recordingsClient.deleteRecording(id)
    }
}