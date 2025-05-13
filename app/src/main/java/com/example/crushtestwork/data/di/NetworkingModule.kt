package com.example.crushtestwork.data.di

import com.example.crushtestwork.data.source.network.clients.PlaygroundRecordingsClient
import com.example.crushtestwork.data.source.network.handler.Handler
import com.example.crushtestwork.domain.client.RecordingsClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkingModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.NONE
            }
            install(ContentNegotiation) {
                // Install the default JSON converter for application/json
                json(Json { ignoreUnknownKeys = true })
                // Register the same converter for text/plain responses
                register(ContentType.Text.Plain, KotlinxSerializationConverter(Json { ignoreUnknownKeys = true }))
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = "b64810a096749e34830892acbc3c11c354d1838c19acf4f34e4743ce5f8d031a",
                            refreshToken = "" // no need
                        )
                    }
                }
            }
        }
    }
    single {
        Handler()
    }
    singleOf(::PlaygroundRecordingsClient).bind(RecordingsClient::class)
}