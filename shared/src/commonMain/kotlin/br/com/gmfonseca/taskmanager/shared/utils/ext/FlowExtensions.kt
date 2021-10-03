package br.com.gmfonseca.taskmanager.shared.utils.ext

import br.com.gmfonseca.taskmanager.shared.client.applicationDispatcher
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.watch(action: suspend (T) -> Unit): Closeable {
    val scope = CoroutineScope(applicationDispatcher + Job())

    onEach { action(it) }.launchIn(scope)

    return scope.buildCloseable()
}

fun CoroutineScope.buildCloseable() = object : Closeable {
    override fun close() {
        cancel()
    }
}