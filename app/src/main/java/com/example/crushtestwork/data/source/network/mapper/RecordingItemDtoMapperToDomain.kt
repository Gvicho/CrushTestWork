package com.example.crushtestwork.data.source.network.mapper

import com.example.crushtestwork.data.source.network.model.RecordingItemDto
import com.example.crushtestwork.domain.model.RecordingItem

fun RecordingItemDto.toDomain(): RecordingItem {
    return RecordingItem(
        id = id,
        title = title ?: "",
        content = content ?: ""
    )
}

fun RecordingItem.toDto(): RecordingItemDto {
    return RecordingItemDto(
        id = null,
        title = title,
        content = content
    )
}