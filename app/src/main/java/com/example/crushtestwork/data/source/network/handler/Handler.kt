package com.example.crushtestwork.data.source.network.handler

import android.util.Log
import com.example.crushtestwork.domain.core.ResultFace
import com.example.crushtestwork.domain.core.errors.NetworkError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

// this class is for decoupling network call handling logic that is same for every method
class Handler {
    suspend inline fun <reified Dto: Any, reified Model: Any, Error: NetworkError> safeApiCall(
        call: () -> HttpResponse,
        getError: (code: Int) -> Error,
        mapper: (Dto) -> Model
    ): ResultFace<Model, Error> {
        try {
            val response = call()
            return if (response.status.isSuccess()) {
                val body = response.body<Dto>()
                ResultFace.Success(
                    mapper(body)
                )
            } else {
                ResultFace.Fail(getError(response.status.value))
            }
        } catch (e: UnresolvedAddressException) {
            // when url is incorrect or unreachable or there is no internet connection
            return ResultFace.Fail(getError(1000))
        } catch (e: SerializationException) {
            // when there is error while serialization
            return ResultFace.Fail(getError(1100))
        } catch (e: Exception) {
            Log.d("tag1234", e.toString())
            // maybe server is down
            return ResultFace.Fail(getError(500))
        }
    }
}