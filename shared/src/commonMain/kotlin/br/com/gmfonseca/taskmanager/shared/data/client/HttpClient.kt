package br.com.gmfonseca.taskmanager.shared.data.client

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

val httpClient
    get() = HttpClient {
        install(JsonFeature) { serializer = KotlinxSerializer() }
    }
