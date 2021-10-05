package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.data.client.listTasks
import br.com.gmfonseca.taskmanager.shared.domain.models.Result
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.utils.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.utils.ext.watchableFlow
import kotlinx.coroutines.delay

interface FetchRemoteTasksRoutineUseCase : UseCase<None, WatchableFlow<Result<List<Task>>>>

class FetchRemoteTasksRoutineUseCaseImpl : FetchRemoteTasksRoutineUseCase {

    override fun invoke(params: None): WatchableFlow<Result<List<Task>>> = watchableFlow {
        while (true) {
            val result = listTasks()

            emit(result)
            delay(10_000L)
        }
    }
}
