package br.com.gmfonseca.taskmanager.shared.client

import br.com.gmfonseca.taskmanager.shared.contract.Result
import br.com.gmfonseca.taskmanager.shared.domain.Task
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.http.content.PartData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.native.concurrent.SharedImmutable

@SharedImmutable
internal expect val applicationDispatcher: CoroutineDispatcher

private val scope = CoroutineScope(Dispatchers.Default + Job())
private const val SERVICE_URL = "http://192.168.10.167:8080"

fun listTasks(callback: (Result<List<Task>>) -> Unit) = scope.launch(applicationDispatcher) {
    try {
        val result: List<Task> = httpClient.get {
            url("$SERVICE_URL/tasks")
        }

        callback(Result.success(result))
    } catch (t: Throwable) {
        callback(Result.failure(t))
        t.printStackTrace()
    }
}

// TODO https://github.com/ktorio/ktor-samples/tree/main/client-mpp
