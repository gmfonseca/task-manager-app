package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.common.Result
import br.com.gmfonseca.taskmanager.shared.common.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.common.ext.watchableFlow
import br.com.gmfonseca.taskmanager.shared.data.repositories.TaskRepositoryImpl
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

interface CompleteTaskUseCase :
    UseCase<CompleteTaskUseCase.Params, WatchableFlow<Result<Boolean>>> {

    data class Params(val id: String, val fileBytes: ByteArray) : UseCase.Params
}

class CompleteTaskUseCaseImpl : CompleteTaskUseCase {
    private val taskRepository: TaskRepository by lazy { TaskRepositoryImpl() }

    override fun invoke(params: CompleteTaskUseCase.Params): WatchableFlow<Result<Boolean>> =
        watchableFlow {
            val result = try {
                val task = taskRepository.completeTask(params.id, params.fileBytes)

                Result.success(task != null)
            } catch (other: Throwable) {
                Result.failure(other)
            }

            emit(result)
        }
}
