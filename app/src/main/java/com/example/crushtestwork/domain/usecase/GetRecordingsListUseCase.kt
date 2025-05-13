package com.example.crushtestwork.domain.usecase

import com.example.crushtestwork.domain.client.RecordingsClient
import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError
import com.example.crushtestwork.domain.model.RecordingItem

// in this case use case is just aggregate, and is used just to pass data
// but in real life we might have filtration's or other business logic here
class GetRecordingsListUseCase(
    private val recordingsClient: RecordingsClient
) {
    suspend operator fun invoke(): ResultFace<List<RecordingItem>, NetworkError> {
        return recordingsClient.getRecordingsList()
    }
}