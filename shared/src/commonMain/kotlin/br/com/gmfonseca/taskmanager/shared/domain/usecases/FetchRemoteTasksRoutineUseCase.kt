package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.client.listTasks
import br.com.gmfonseca.taskmanager.shared.contract.Result
import br.com.gmfonseca.taskmanager.shared.domain.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FetchRemoteTasksRoutineUseCase : UseCase<None, Flow<Result<List<Task>>>>

class FetchRemoteTasksRoutineUseCaseImpl : FetchRemoteTasksRoutineUseCase {

    override fun invoke(params: None) = flow {
        while (true) {
            val result = listTasks()

            emit(result)
            delay(10_000L)
        }
    }
}
