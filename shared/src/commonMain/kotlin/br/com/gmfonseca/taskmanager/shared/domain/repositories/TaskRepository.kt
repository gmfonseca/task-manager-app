package br.com.gmfonseca.taskmanager.shared.domain.repositories

import br.com.gmfonseca.taskmanager.shared.domain.models.Result
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

interface TaskRepository {
    suspend fun fetchTasks(): Result<List<Task>>
    suspend fun completeTask(id: String, imageBytes: ByteArray): Result<Boolean>
}