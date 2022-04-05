package br.com.gmfonseca.taskmanager.shared.data.datasources

import br.com.gmfonseca.taskmanager.shared.data.remote.task.TaskClient
import br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos.TaskDto

interface TaskRemoteDataSource {
    suspend fun fetchTasks(): List<TaskDto>
    suspend fun completeTask(id: String, imageBytes: ByteArray): TaskDto?
    suspend fun createTask(task: TaskDto): TaskDto?
}

class TaskRemoteDataSourceImpl : TaskRemoteDataSource {
    override suspend fun fetchTasks(): List<TaskDto> = TaskClient.listTasks()

    override suspend fun completeTask(id: String, imageBytes: ByteArray): TaskDto? =
        TaskClient.finishTask(id, imageBytes)

    override suspend fun createTask(task: TaskDto): TaskDto? = TaskClient.createTask(task)
}
