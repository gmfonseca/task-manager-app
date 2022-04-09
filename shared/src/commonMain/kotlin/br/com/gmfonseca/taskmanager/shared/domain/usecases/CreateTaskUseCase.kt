package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.common.Result
import br.com.gmfonseca.taskmanager.shared.common.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.common.ext.watchableFlow
import br.com.gmfonseca.taskmanager.shared.data.repositories.TaskRepositoryImpl
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

interface CreateTaskUseCase : UseCase<CreateTaskUseCase.Params, Flow<Result<Task>>> {

    data class Params(val title: String, val description: String) : UseCase.Params
}

class CreateTaskUseCaseImpl : CreateTaskUseCase {
    private val taskRepository: TaskRepository by lazy { TaskRepositoryImpl() }

    override fun invoke(params: CreateTaskUseCase.Params): WatchableFlow<Result<Task>> =
        watchableFlow {
            val result = try {
                val (title, description) = params
                taskRepository.createTask(title, description)
                    ?.let { Result.success(it) }
                    ?: Result.failure(Error("Failed to create the task"))
            } catch (other: Throwable) {
                Result.failure(other)
            }

            emit(result)
        }
}
