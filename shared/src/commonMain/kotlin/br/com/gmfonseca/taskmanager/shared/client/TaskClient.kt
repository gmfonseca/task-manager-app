package br.com.gmfonseca.taskmanager.shared.client

import br.com.gmfonseca.taskmanager.shared.contract.Result
import br.com.gmfonseca.taskmanager.shared.domain.Task
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.native.concurrent.SharedImmutable

@SharedImmutable
internal expect val applicationDispatcher: CoroutineDispatcher

val scope get() = CoroutineScope(Dispatchers.Default + Job())
private const val SERVICE_URL = "http://192.168.10.167:8080"

suspend fun listTasks(): Result<List<Task>> = try {
    val result: List<Task> = httpClient.get {
        url("$SERVICE_URL/tasks")
    }

    Result.success(result)
} catch (t: Throwable) {
    t.printStackTrace()
    Result.failure(t)
}

// TODO https://github.com/ktorio/ktor-samples/tree/main/client-mpp
