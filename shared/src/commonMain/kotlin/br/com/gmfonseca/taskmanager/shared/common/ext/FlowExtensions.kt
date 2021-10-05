package br.com.gmfonseca.taskmanager.shared.common.ext

import br.com.gmfonseca.taskmanager.shared.common.Closeable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

val <T> Flow<T>.asWatchable: WatchableFlow<T> get() = WatchableFlow(this)

fun <T> Flow<T>.watch(action: (T) -> Unit): Closeable = asWatchable.watch(action)

fun <T> watchableFlow(block: suspend FlowCollector<T>.() -> Unit) =
    flow(block).asWatchable

