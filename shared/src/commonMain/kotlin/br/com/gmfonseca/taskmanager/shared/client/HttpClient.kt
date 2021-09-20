package br.com.gmfonseca.taskmanager.shared.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.http.Url

val client = HttpClient {
    engine {
        proxy = ProxyBuilder.http(Url("http://192.168.10.167:8080"))
    }
}
