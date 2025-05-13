package com.example.crushtestwork.data.source.network.clients

import com.example.crushtestwork.data.source.network.endpointProvider.TestEndpointProvider
import com.example.crushtestwork.data.source.network.handler.Handler
import com.example.crushtestwork.data.source.network.mapper.toDomain
import com.example.crushtestwork.data.source.network.mapper.toDto
import com.example.crushtestwork.data.source.network.model.DeleteRecordingResponseDto
import com.example.crushtestwork.data.source.network.model.RecordingItemDto
import com.example.crushtestwork.domain.client.RecordingsClient
import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError
import com.example.crushtestwork.domain.core.errors.getNetworkError
import com.example.crushtestwork.domain.model.RecordingItem
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PlaygroundRecordingsClient(
    private val httpClient: HttpClient,
    private val handle: Handler,
) : RecordingsClient {

    override suspend fun getRecordingsList(): ResultFace<List<RecordingItem>, NetworkError> {
        return handle.safeApiCall<List<RecordingItemDto>, List<RecordingItem>, NetworkError>(
            call = {
                httpClient.get(
                    urlString = TestEndpointProvider.getUrl()
                ) {
                    url {
                        parameters.append("path", "list")
                    }
                }
            },
            getError = {
                getNetworkError(it)
            },
            mapper = { list ->
                list.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun addRecording(newRecording: RecordingItem): ResultFace<Unit, NetworkError> {
        return handle.safeApiCall<Unit, Unit, NetworkError>(
            call = {
                httpClient.post(
                    urlString = TestEndpointProvider.getUrl()
                ) {
                    url {
                        parameters.append("path", "create")
                    }
                    contentType(ContentType.Application.Json)
                    setBody(newRecording.toDto())
                }
            },
            getError = {
                getNetworkError(it)
            },
            mapper = { }
        )
    }

    override suspend fun deleteRecording(id: String): ResultFace<Boolean, NetworkError> {
        return handle.safeApiCall<DeleteRecordingResponseDto, Boolean, NetworkError>(
            call = {
                httpClient.delete(
                    urlString = TestEndpointProvider.getUrl()
                ) {
                    url {
                        parameters.append("path", "delete")
                        parameters.append("id", id)
                    }
                    contentType(ContentType.Application.Json)
                }
            },
            getError = {
                getNetworkError(it)
            },
            mapper = { it.success }
        )
    }
}