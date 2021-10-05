package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.domain.models.Result
import br.com.gmfonseca.taskmanager.shared.data.repositories.TaskRepositoryImpl
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository
import br.com.gmfonseca.taskmanager.shared.utils.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.utils.ext.watchableFlow
import kotlinx.coroutines.flow.Flow

interface CompleteTasksUseCase :
    UseCase<CompleteTasksUseCase.Params, Flow<Result<Boolean>>> {

    data class Params(val id: String, val fileBytes: ByteArray) : UseCase.Params()
}

class CompleteTasksUseCaseImpl : CompleteTasksUseCase {
    private val taskRepository: TaskRepository by lazy { TaskRepositoryImpl() }

    override fun invoke(params: CompleteTasksUseCase.Params): WatchableFlow<Result<Boolean>> =
        watchableFlow {
            val result = taskRepository.completeTask(params.id, params.fileBytes)

            emit(result)
        }
}
