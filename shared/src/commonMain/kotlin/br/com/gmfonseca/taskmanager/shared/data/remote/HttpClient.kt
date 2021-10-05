package br.com.gmfonseca.taskmanager.shared.data.remote

import br.com.gmfonseca.taskmanager.shared.common.network.exceptions.ClientHttpError
import br.com.gmfonseca.taskmanager.shared.common.network.exceptions.ServerHttpError
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.ServerResponseException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

val httpClient
    get() = HttpClient {
        install(JsonFeature) { serializer = KotlinxSerializer() }

        HttpResponseValidator {
            handleResponseException {
                when (it) {
                    is ClientRequestException -> throw ClientHttpError(it.response.status.value)
                    is ServerResponseException -> throw ServerHttpError(it.response.status.value)
                }
            }
        }

    }
