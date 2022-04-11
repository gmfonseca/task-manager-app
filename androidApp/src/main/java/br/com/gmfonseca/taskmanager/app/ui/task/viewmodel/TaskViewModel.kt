package br.com.gmfonseca.taskmanager.app.ui.task.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.gmfonseca.taskmanager.app.ui.task.model.TaskFormUiState
import br.com.gmfonseca.taskmanager.app.ui.task.model.TasksListUiState
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import kotlinx.coroutines.flow.StateFlow

abstract class TaskViewModel : ViewModel() {
    abstract val listUiState: StateFlow<TasksListUiState>
    abstract val formUiState: StateFlow<TaskFormUiState>
    abstract var completingTask: Task?

    abstract fun beginRoutine(context: Context)
    abstract fun completeTask(fileBytes: ByteArray, context: Context)
    abstract fun changeFilter(newOption: FilterOption)
    abstract fun selectTask(task: Task?, showDialog: Boolean = false)
    abstract fun createTask(onSuccess: () -> Unit, onError: () -> Unit)
    abstract fun clearFormUiState()
    abstract fun clearListSnackbarState()

    abstract fun updateForm(
        title: String = formUiState.value.title,
        description: String = formUiState.value.description
    )
}
