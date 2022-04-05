package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.common.Result
import br.com.gmfonseca.taskmanager.shared.common.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.common.ext.watchableFlow
import br.com.gmfonseca.taskmanager.shared.data.repositories.TaskRepositoryImpl
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

interface CreateTaskUseCase :
    UseCase<CreateTaskUseCase.Params, Flow<Result<Boolean>>> {

    data class Params(val title: String, val description: String) : UseCase.Params
}

class CreateTaskUseCaseImpl : CreateTaskUseCase {
    private val taskRepository: TaskRepository by lazy { TaskRepositoryImpl() }

    override fun invoke(params: CreateTaskUseCase.Params): WatchableFlow<Result<Boolean>> =
        watchableFlow {
            val result = try {
                val (title, description) = params
                val task = taskRepository.createTask(title, description)

                Result.success(task != null)
            } catch (other: Throwable) {
                Result.failure(other)
            }

            emit(result)
        }
}
