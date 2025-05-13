package com.example.crushtestwork.domain.client

import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError
import com.example.crushtestwork.domain.model.RecordingItem

interface RecordingsClient {
    suspend fun getRecordingsList(): ResultFace<List<RecordingItem>, NetworkError>
    suspend fun addRecording(newRecording: RecordingItem): ResultFace<Unit, NetworkError>
    suspend fun deleteRecording(id: String): ResultFace<Boolean, NetworkError>
    suspend fun editRecording(editedRecording: RecordingItem): ResultFace<Unit, NetworkError>
}