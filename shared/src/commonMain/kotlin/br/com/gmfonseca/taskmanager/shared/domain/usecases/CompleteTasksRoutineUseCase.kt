package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.client.completeTask
import br.com.gmfonseca.taskmanager.shared.contract.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CompleteTasksRoutineUseCase :
    UseCase<CompleteTasksRoutineUseCase.Params, Flow<Result<Boolean>>> {

    data class Params(val id: String, val fileBytes: ByteArray) : UseCase.Params()
}

class CompleteTasksRoutineUseCaseImpl : CompleteTasksRoutineUseCase {

    override fun invoke(params: CompleteTasksRoutineUseCase.Params) = flow {
        val result = completeTask(params.id, params.fileBytes)

        emit(result)
    }
}
