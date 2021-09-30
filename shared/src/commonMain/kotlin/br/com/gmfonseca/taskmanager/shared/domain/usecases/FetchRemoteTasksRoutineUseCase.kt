package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.client.applicationDispatcher
import br.com.gmfonseca.taskmanager.shared.client.listTasks
import br.com.gmfonseca.taskmanager.shared.client.scope
import br.com.gmfonseca.taskmanager.shared.contract.None
import br.com.gmfonseca.taskmanager.shared.contract.Result
import br.com.gmfonseca.taskmanager.shared.contract.UseCase
import br.com.gmfonseca.taskmanager.shared.domain.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface FetchRemoteTasksRoutineUseCase : UseCase<None, Flow<Result<List<Task>>>>

class FetchRemoteTasksRoutineUseCaseImpl : FetchRemoteTasksRoutineUseCase {

    override fun invoke(param: None) = flow {
        while (true) {
            val result = listTasks()

            emit(result)
            delay(10_000L)
        }
    }
}

operator fun FetchRemoteTasksRoutineUseCaseImpl.invoke(callback: (Result<List<Task>>) -> Unit) {
    scope.launch(applicationDispatcher) {
        invoke(None).collect {
            callback(it)
        }
    }
}
