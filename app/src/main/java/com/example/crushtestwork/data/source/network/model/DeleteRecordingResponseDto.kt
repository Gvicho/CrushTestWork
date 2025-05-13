package com.example.crushtestwork.data.source.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteRecordingResponseDto(
    @SerialName("success") val success: Boolean
)