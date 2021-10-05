package br.com.gmfonseca.taskmanager.shared.data.repositories

import br.com.gmfonseca.taskmanager.shared.domain.models.Result
import br.com.gmfonseca.taskmanager.shared.data.datasources.TaskRemoteDataSource
import br.com.gmfonseca.taskmanager.shared.data.datasources.TaskRemoteDataSourceImpl
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository

class TaskRepositoryImpl(
    private val taskRemoteDataSource: TaskRemoteDataSource = TaskRemoteDataSourceImpl()
) : TaskRepository {
    override suspend fun fetchTasks(): Result<List<Task>> {
        return taskRemoteDataSource.fetchTasks()
    }

    override suspend fun completeTask(id: String, imageBytes: ByteArray): Result<Boolean> {
        return taskRemoteDataSource.completeTask(id, imageBytes)
    }
}