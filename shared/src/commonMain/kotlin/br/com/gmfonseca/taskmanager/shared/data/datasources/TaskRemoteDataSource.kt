package br.com.gmfonseca.taskmanager.shared.data.datasources

import br.com.gmfonseca.taskmanager.shared.data.client.listTasks
import br.com.gmfonseca.taskmanager.shared.domain.models.Result
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.data.client.finishTask

interface TaskRemoteDataSource {
    suspend fun fetchTasks(): Result<List<Task>>
    suspend fun completeTask(id: String, imageBytes: ByteArray): Result<Boolean>
}

class TaskRemoteDataSourceImpl : TaskRemoteDataSource {
    override suspend fun fetchTasks(): Result<List<Task>> {
        return listTasks()
    }

    override suspend fun completeTask(id: String, imageBytes: ByteArray): Result<Boolean> {
        return finishTask(id, imageBytes)
    }
}