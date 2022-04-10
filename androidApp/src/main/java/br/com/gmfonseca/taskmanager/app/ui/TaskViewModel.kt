package br.com.gmfonseca.taskmanager.app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.usecases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

class TaskViewModelImpl : TaskViewModel() {
    private val fetchRemoteTasksRoutine by lazy { FetchRemoteTasksRoutineUseCaseImpl() }
    private val completeTasksRoutine by lazy { CompleteTaskUseCaseImpl() }
    private val createTaskUseCase by lazy { CreateTaskUseCaseImpl() }

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

    override fun selectTask(task: Task?, showDialog: Boolean) {
        listUiState.value.run {
            if (currentTask != task || isInfoDialogShown != showDialog) {
                _listUiState.value = copy(
                    currentTask = task, isInfoDialogShown = task != null && showDialog
                )
            }
        }
    }

    override fun updateForm(title: String, description: String) {
        _formUiState.value = formUiState.value.copy(
            title = title, description = description
        )
    }

    override fun createTask(onSuccess: () -> Unit, onError: () -> Unit) {
        val (title, description) = formUiState.value

        _formUiState.value = formUiState.value.copy(hasError = false)

        createTaskUseCase(CreateTaskUseCase.Params(title, description))
            .watch { result ->
                viewModelScope.launch {
                    if (result.isSuccess) {
                        _listUiState.emit(
                            listUiState.value.copy(
                                snackbarData = SnackbarNotificationData.Success(
                                    text = "Successfully created the task #${result.get().id}"
                                )
                            )
                        )

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

class TaskViewModelStub(
    tasks: List<Task>? = null,
    currentTask: Task? = null,
) : TaskViewModel() {
    override val listUiState: StateFlow<TasksListUiState> = MutableStateFlow(
        TasksListUiState(
            tasks = tasks ?: listOf(
                Task(
                    id = "1",
                    title = "First task title",
                    description = "First task description",
                ),
                Task(
                    id = "2",
                    title = "Second cool task title",
                    description = "This is the task description that can reach at most 2 lines and may be filling well when a long description is suppo",
                    isCompleted = true
                ),
            ),
            currentTask = currentTask
        )
    )
    override val formUiState: StateFlow<TaskFormUiState> = MutableStateFlow(
        TaskFormUiState("This is the title", "And this is the description of the titled task")
    )

    override var completingTask: Task? = null
    override fun beginRoutine(context: Context) = Unit
    override fun completeTask(fileBytes: ByteArray, context: Context) = Unit
    override fun changeFilter(newOption: FilterOption) = Unit
    override fun selectTask(task: Task?, showDialog: Boolean) = Unit
    override fun updateForm(title: String, description: String) = Unit
    override fun createTask(onSuccess: () -> Unit, onError: () -> Unit) = Unit
    override fun clearFormUiState() = Unit
    override fun clearListSnackbarState() = Unit
}

data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilterOption: FilterOption = FilterOption.ALL,
    val currentTask: Task? = null,
    val isInfoDialogShown: Boolean = false,
    val snackbarData: SnackbarNotificationData? = null
)

data class TaskFormUiState(
    val title: String = "",
    val description: String = "",
    val hasError: Boolean = false
) {
    val isCompleted get() = title.isNotBlank() && description.isNotBlank()
}
