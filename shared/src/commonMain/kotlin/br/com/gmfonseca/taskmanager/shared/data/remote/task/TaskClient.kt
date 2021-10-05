package br.com.gmfonseca.taskmanager.shared.data.remote.task

import br.com.gmfonseca.taskmanager.shared.common.Constants.SERVICE_URL
import br.com.gmfonseca.taskmanager.shared.data.remote.httpClient
import br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos.TaskDto
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully

suspend fun listTasks(): List<TaskDto> = httpClient.get {
    parameter("completed", false)
    url("$SERVICE_URL/tasks")
}

suspend fun finishTask(id: String, bytes: ByteArray): TaskDto? = httpClient.put {
    url("$SERVICE_URL/tasks/$id")

    body = MultiPartFormDataContent(
        formData {
            appendInput(
                key = "image",
                headers = Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=file.jpeg")
                }
            ) { buildPacket { writeFully(bytes) } }
        }
    )
}
