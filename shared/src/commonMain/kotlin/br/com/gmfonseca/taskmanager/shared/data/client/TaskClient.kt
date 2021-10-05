package br.com.gmfonseca.taskmanager.shared.data.client

import br.com.gmfonseca.taskmanager.shared.domain.models.Result
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully

private const val SERVICE_URL = "http://192.168.10.167:8080"

suspend fun listTasks(): Result<List<Task>> = try {
    val result: List<Task> = httpClient.get {
        parameter("completed", false)
        url("$SERVICE_URL/tasks")
    }

    Result.success(result)
} catch (t: Throwable) {
    t.printStackTrace()
    Result.failure(t)
}

suspend fun finishTask(id: String, bytes: ByteArray): Result<Boolean> = try {
    httpClient.put<Task> {
        url("$SERVICE_URL/tasks/$id")
        method = HttpMethod.Put

        body = MultiPartFormDataContent(
            formData {
                appendInput("image", headers = Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=awesomeupload.jpeg")
                }) {
                    buildPacket { writeFully(bytes) }
                }
            }
        )
    }

    Result.success(true)
} catch (t: Throwable) {
    t.printStackTrace()
    Result.failure(t)
}

// TODO https://github.com/ktorio/ktor-samples/tree/main/client-mpp
