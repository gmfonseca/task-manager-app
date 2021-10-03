package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.client.completeTask
import br.com.gmfonseca.taskmanager.shared.contract.Result
import br.com.gmfonseca.taskmanager.shared.utils.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.utils.ext.watchableFlow
import kotlinx.coroutines.flow.Flow

interface CompleteTasksRoutineUseCase :
    UseCase<CompleteTasksRoutineUseCase.Params, Flow<Result<Boolean>>> {

    data class Params(val id: String, val fileBytes: ByteArray) : UseCase.Params()
}

class CompleteTasksRoutineUseCaseImpl : CompleteTasksRoutineUseCase {

    override fun invoke(params: CompleteTasksRoutineUseCase.Params): WatchableFlow<Result<Boolean>> = watchableFlow {
        val result = completeTask(params.id, params.fileBytes)

        emit(result)
    }
}
