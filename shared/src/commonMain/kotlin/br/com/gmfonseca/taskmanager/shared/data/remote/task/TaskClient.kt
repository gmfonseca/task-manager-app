package br.com.gmfonseca.taskmanager.shared.data.remote.task

import br.com.gmfonseca.taskmanager.shared.common.Constants.SERVICE_URL
import br.com.gmfonseca.taskmanager.shared.data.remote.httpClient
import br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos.TaskDto
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

object TaskClient {
    suspend fun listTasks(): List<TaskDto> = httpClient.get {
        parameter("completed", null)
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

    suspend fun createTask(task: TaskDto): TaskDto? = httpClient.post {
        url("${SERVICE_URL}/tasks")
        contentType(ContentType.Application.Json)
        body = task
    }
}
