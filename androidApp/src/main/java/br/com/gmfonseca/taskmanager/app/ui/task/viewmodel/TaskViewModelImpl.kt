package br.com.gmfonseca.taskmanager.app.ui.task.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.task.model.TaskFormUiState
import br.com.gmfonseca.taskmanager.app.ui.task.model.TasksListUiState
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTaskUseCase
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CreateTaskUseCase
import br.com.gmfonseca.taskmanager.shared.domain.usecases.FetchRemoteTasksRoutineUseCase
import br.com.gmfonseca.taskmanager.shared.domain.usecases.None
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModelImpl(
    private val fetchRemoteTasksRoutine: FetchRemoteTasksRoutineUseCase,
    private val completeTasksRoutine: CompleteTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
) : TaskViewModel() {

    private val _listUiState = MutableStateFlow(TasksListUiState())
    override val listUiState: StateFlow<TasksListUiState> get() = _listUiState

    private val _formUiState = MutableStateFlow(TaskFormUiState())
    override val formUiState: StateFlow<TaskFormUiState> get() = _formUiState

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
                _listUiState.value = listUiState.value.copy(tasks = filteredTasksByState())
            }
        }
    }

    override fun completeTask(fileBytes: ByteArray, context: Context) {
        val taskId = completingTask?.id
            ?: return run {
                _listUiState.value = listUiState.value.copy(
                    snackbarData = SnackbarNotificationData.Failure(
                        text = "Failed to complete an unknown task"
                    )
                )
            }

        completeTasksRoutine(CompleteTaskUseCase.Params(taskId, fileBytes))
            .watch { result ->
                viewModelScope.launch {
                    val newListUiState = if (result.isSuccess && result.get()) {
                        _allTasks.removeIf { it.id == taskId }

                        listUiState.value.copy(
                            tasks = filteredTasksByState(),
                            snackbarData = SnackbarNotificationData.Success(
                                text = "Successfully completed the task #$taskId"
                            )
                        )
                    } else {
                        listUiState.value.copy(
                            snackbarData = SnackbarNotificationData.Failure(
                                text = "Failed to complete the task #$taskId"
                            )
                        )
                    }

                    _listUiState.emit(newListUiState)
                }
            }
    }

    override fun changeFilter(newOption: FilterOption) {
        if (listUiState.value.selectedFilterOption != newOption) {
            _listUiState.value = listUiState.value.copy(
                selectedFilterOption = newOption,
                tasks = filteredTasksBy(newOption)
            )
        }
    }

    override fun selectTask(task: Task?, showDialog: Boolean) = with(listUiState.value) {
        if (currentTask != task || isInfoDialogShown != showDialog) {
            _listUiState.value = copy(
                currentTask = task,
                isInfoDialogShown = task != null && showDialog
            )
        }
    }

    override fun updateForm(title: String, description: String) {
        _formUiState.value = formUiState.value.copy(
            title = title,
            description = description
        )
    }

    override fun createTask(onSuccess: () -> Unit, onError: () -> Unit) {
        val (title, description) = formUiState.value

        _formUiState.value = formUiState.value.copy(hasError = false)

        createTaskUseCase(CreateTaskUseCase.Params(title, description)).watch { result ->
            viewModelScope.launch {
                if (result.isSuccess) {
                    _listUiState.emit(
                        listUiState.value.copy(
                            snackbarData = SnackbarNotificationData.Success(
                                text = "Successfully created the task #${result.get().id}"
                            )
                        )
                    )

                    clearFormUiState()
                    onSuccess()
                } else {
                    _formUiState.emit(formUiState.value.copy(hasError = true))

                    onError()
                }
            }
        }
    }

    override fun clearFormUiState() {
        _formUiState.value = TaskFormUiState()
    }

    override fun clearListSnackbarState() {
        if (listUiState.value.snackbarData != null) {
            _listUiState.value = listUiState.value.copy(snackbarData = null)
        }
    }

    private fun filteredTasksByState() = filteredTasksBy(listUiState.value.selectedFilterOption)

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
