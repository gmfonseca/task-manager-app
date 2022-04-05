package br.com.gmfonseca.taskmanager.shared.domain.usecases

import br.com.gmfonseca.taskmanager.shared.common.Result
import br.com.gmfonseca.taskmanager.shared.common.ext.WatchableFlow
import br.com.gmfonseca.taskmanager.shared.common.ext.watchableFlow
import br.com.gmfonseca.taskmanager.shared.data.repositories.TaskRepositoryImpl
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository
import kotlinx.coroutines.delay

interface FetchRemoteTasksRoutineUseCase : UseCase<None, WatchableFlow<Result<List<Task>>>>

class FetchRemoteTasksRoutineUseCaseImpl : FetchRemoteTasksRoutineUseCase {
    private val taskRepository: TaskRepository by lazy { TaskRepositoryImpl() }

    override fun invoke(params: None): WatchableFlow<Result<List<Task>>> = watchableFlow {
        while (true) {
            val result = try {
                Result.success(taskRepository.fetchTasks())
            } catch (other: Throwable) {
                Result.failure(other)
            }

            emit(result)
            delay(10_000L)
        }
    }
}
