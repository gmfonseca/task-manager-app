package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.client.listTasks
import br.com.gmfonseca.taskmanager.shared.contract.Result
import br.com.gmfonseca.taskmanager.shared.domain.model.Task
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
