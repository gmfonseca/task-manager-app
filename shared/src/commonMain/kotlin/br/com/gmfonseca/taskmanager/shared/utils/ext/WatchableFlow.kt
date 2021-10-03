package br.com.gmfonseca.taskmanager.shared.utils.ext

import br.com.gmfonseca.taskmanager.shared.domain.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WatchableFlow<T>(private val parent: Flow<T>) : Flow<T> by parent {
    fun watch(action: (T) -> Unit): Closeable {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)

        onEach { action(it) }.launchIn(scope)

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}
