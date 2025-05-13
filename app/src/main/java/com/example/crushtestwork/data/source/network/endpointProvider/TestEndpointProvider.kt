package com.example.crushtestwork.data.source.network.endpointProvider

object TestEndpointProvider {
    private const val PLAYGROUND_BASE_URL = "https://api.framework.ge/playground/"
    fun getUrl(): String {
        return PLAYGROUND_BASE_URL
    }
}