package br.com.gmfonseca.taskmanager.app.ui.task.model

data class TaskFormUiState(
    val title: String = "",
    val description: String = "",
    val hasError: Boolean = false
) {
    val isCompleted get() = title.isNotBlank() && description.isNotBlank()
}
