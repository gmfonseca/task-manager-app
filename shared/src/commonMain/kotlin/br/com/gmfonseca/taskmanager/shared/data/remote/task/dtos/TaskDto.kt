package br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String = "",
    val title: String,
    val description: String,
    @SerialName("is_completed") var isCompleted: Boolean = false,
    @SerialName("image_path") var imagePath: String? = null
)
