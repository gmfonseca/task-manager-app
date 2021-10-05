package br.com.gmfonseca.taskmanager.shared.data.datasources

import br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos.TaskDto
import br.com.gmfonseca.taskmanager.shared.data.remote.task.finishTask
import br.com.gmfonseca.taskmanager.shared.data.remote.task.listTasks

interface TaskRemoteDataSource {
    suspend fun fetchTasks(): List<TaskDto>
    suspend fun completeTask(id: String, imageBytes: ByteArray): TaskDto?
}

class TaskRemoteDataSourceImpl : TaskRemoteDataSource {
    override suspend fun fetchTasks(): List<TaskDto> = listTasks()

    override suspend fun completeTask(id: String, imageBytes: ByteArray): TaskDto? =
        finishTask(id, imageBytes)
}
