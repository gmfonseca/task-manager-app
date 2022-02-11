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
    abstract var completingTask: Task?

    abstract fun beginRoutine(context: Context)
    abstract fun completeTask(fileBytes: ByteArray, context: Context)
    abstract fun changeFilter(newOption: FilterOption)
    abstract fun selectTask(task: Task?)
}

class TaskViewModelImpl : TaskViewModel() {
    private val fetchRemoteTasksRoutine by lazy { FetchRemoteTasksRoutineUseCaseImpl() }
    private val completeTasksRoutine by lazy { CompleteTaskUseCaseImpl() }

    private val _uiState = MutableStateFlow(TasksUiState())
    override val uiState: StateFlow<TasksUiState> get() = _uiState

    private var _allTasks = mutableListOf<Task>()

    override var completingTask: Task? = null

    private var hasActiveRoutine = false

    override fun beginRoutine(context: Context) {
        if (!hasActiveRoutine) {
            hasActiveRoutine = true

            fetchRemoteTasksRoutine(None).watch { result ->
                result.getOrNull()?.let {
                    _allTasks = it.sortedBy(Task::isCompleted).toMutableList()
                }
                _uiState.value = uiState.value.copy(tasks = filteredTasksByState())
            }
        }
    }

    override fun completeTask(fileBytes: ByteArray, context: Context) {
        val taskId = completingTask?.id ?: return

        completeTasksRoutine(
            CompleteTaskUseCase.Params(taskId, fileBytes)
        ).watch { result ->
            if (result.isSuccess && result.get()) {
                viewModelScope.launch {
                    _allTasks.removeIf { it.id == taskId }
                    _uiState.emit(uiState.value.copy(tasks = filteredTasksByState()))
                }
            }
        }
    }

    override fun changeFilter(newOption: FilterOption) {
        if (uiState.value.selectedFilterOption != newOption) {
            _uiState.value = uiState.value.copy(
                selectedFilterOption = newOption,
                tasks = filteredTasksBy(newOption)
            )
        }
    }

    override fun selectTask(task: Task?) {
        _uiState.value = uiState.value.copy(
            currentTask = task
        )
    }

    private fun filteredTasksByState() = filteredTasksBy(uiState.value.selectedFilterOption)

    private fun filteredTasksBy(option: FilterOption): List<Task> {
        val isCompleted = when (option) {
            FilterOption.DONE -> true
            FilterOption.PENDING -> false
            FilterOption.ALL -> null
        }

        return if (isCompleted != null) {
            _allTasks.filter { it.isCompleted == isCompleted }
        } else {
            _allTasks
        }
    }
}

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilterOption: FilterOption = FilterOption.ALL,
    val currentTask: Task? = null
)
