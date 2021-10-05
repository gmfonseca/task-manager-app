package br.com.gmfonseca.taskmanager.shared.data.repositories

import br.com.gmfonseca.taskmanager.shared.data.datasources.TaskRemoteDataSource
import br.com.gmfonseca.taskmanager.shared.data.datasources.TaskRemoteDataSourceImpl
import br.com.gmfonseca.taskmanager.shared.data.mappers.asDomain
import br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos.TaskDto
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.repositories.TaskRepository

class TaskRepositoryImpl(
    private val taskRemoteDataSource: TaskRemoteDataSource = TaskRemoteDataSourceImpl()
) : TaskRepository {
    override suspend fun fetchTasks(): List<Task> =
        taskRemoteDataSource.fetchTasks().map(TaskDto::asDomain)

    override suspend fun completeTask(id: String, imageBytes: ByteArray): Task? =
        taskRemoteDataSource.completeTask(id, imageBytes)?.asDomain
}
