package br.com.gmfonseca.taskmanager.shared.domain.repositories

import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

interface TaskRepository {
    suspend fun fetchTasks(): List<Task>
    suspend fun completeTask(id: String, imageBytes: ByteArray): Task?
}
