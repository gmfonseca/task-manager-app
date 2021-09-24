package br.com.gmfonseca.taskmanager.shared.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.Url

val httpClient get() = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}
