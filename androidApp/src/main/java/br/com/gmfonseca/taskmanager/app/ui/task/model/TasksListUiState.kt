package br.com.gmfonseca.taskmanager.app.ui.task.model

import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilterOption: FilterOption = FilterOption.ALL,
    val currentTask: Task? = null,
    val isInfoDialogShown: Boolean = false,
    val snackbarData: SnackbarNotificationData? = null
)
