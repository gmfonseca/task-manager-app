package br.com.gmfonseca.taskmanager.shared.data.mappers

import br.com.gmfonseca.taskmanager.shared.data.remote.task.dtos.TaskDto
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

val TaskDto.asDomain
    get() = Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        imagePath = imagePath
    )

val Task.asDto
    get() = TaskDto(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        imagePath = imagePath
    )
