package br.com.gmfonseca.taskmanager.shared.domain

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String = "",
    val title: String,
    val description: String,
    var isCompleted: Boolean = false,
    var imagePath: String? = null
)
