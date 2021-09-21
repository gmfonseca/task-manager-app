package br.com.gmfonseca.taskmanager.shared.client

import br.com.gmfonseca.taskmanager.shared.domain.Task
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.utils.io.core.use
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.native.concurrent.SharedImmutable

@SharedImmutable
internal expect val ApplicationDispatcher: CoroutineDispatcher

private val scope = CoroutineScope(Dispatchers.Default + Job())

fun listTasks(callback: (Any) -> Unit) = scope.launch(ApplicationDispatcher) {
    HttpClient().use { client ->
        try {
            val result: List<Task> = client.get {
                url("http://192.168.10.167:8080/tasks")
            }

            callback(result)
        } catch (t: Throwable) {
            callback(t)
        }
    }
}

// TODO https://github.com/ktorio/ktor-samples/tree/main/client-mpp
