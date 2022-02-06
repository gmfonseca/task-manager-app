package br.com.gmfonseca.taskmanager.app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTaskUseCase
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTaskUseCaseImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.FetchRemoteTasksRoutineUseCaseImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.None
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel() {
    abstract val uiState: StateFlow<TasksUiState>
    abstract var currentTask: Task?

    abstract fun beginRoutine(context: Context)
    abstract fun completeTask(fileBytes: ByteArray, context: Context)
    abstract fun changeFilter(newOption: FilterOption)
}

class TaskViewModelImpl : TaskViewModel() {
    private val fetchRemoteTasksRoutine by lazy { FetchRemoteTasksRoutineUseCaseImpl() }
    private val completeTasksRoutine by lazy { CompleteTaskUseCaseImpl() }

    private val _uiState = MutableStateFlow(TasksUiState())
    override val uiState: StateFlow<TasksUiState> get() = _uiState

    override var currentTask: Task? = null

    private var hasActiveRoutine = false

    override fun beginRoutine(context: Context) {
        if (!hasActiveRoutine) {
            hasActiveRoutine = true

            fetchRemoteTasksRoutine(None).watch {
                _uiState.value = uiState.value.copy(tasks = it.get())
            }
        }
    }

    override fun completeTask(fileBytes: ByteArray, context: Context) {
        completeTasksRoutine(
            CompleteTaskUseCase.Params("${currentTask?.id}", fileBytes)
        ).watch { result ->
            if (result.isSuccess && result.get()) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.run { copy(tasks = tasks.filter { it != currentTask }) })
                }
            }
        }
    }

    override fun changeFilter(newOption: FilterOption) {
        if (uiState.value.selectedFilterOption != newOption) {
            _uiState.value = uiState.value.copy(selectedFilterOption = newOption)
        }
    }
}

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilterOption: FilterOption = FilterOption.ALL
)
