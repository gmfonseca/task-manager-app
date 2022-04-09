package br.com.gmfonseca.taskmanager.app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.model.FilterOption
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.model.Status
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.usecases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel() {
    abstract val uiState: StateFlow<TasksUiState>
    abstract val formState: StateFlow<TaskFormState>
    abstract var completingTask: Task?

    abstract fun beginRoutine(context: Context)
    abstract fun completeTask(fileBytes: ByteArray, context: Context)
    abstract fun changeFilter(newOption: FilterOption)
    abstract fun selectTask(task: Task?, showDialog: Boolean = false)
    abstract fun createTask(onSuccess: (Status, String) -> Unit, onError: () -> Unit)
    abstract fun clearFormState()

    abstract fun updateForm(
        title: String = formState.value.title,
        description: String = formState.value.description
    )
}

class TaskViewModelImpl : TaskViewModel() {
    private val fetchRemoteTasksRoutine by lazy { FetchRemoteTasksRoutineUseCaseImpl() }
    private val completeTasksRoutine by lazy { CompleteTaskUseCaseImpl() }
    private val createTaskUseCase by lazy { CreateTaskUseCaseImpl() }

    private val _uiState = MutableStateFlow(TasksUiState())
    override val uiState: StateFlow<TasksUiState> get() = _uiState

    private val _formState = MutableStateFlow(TaskFormState())
    override val formState: StateFlow<TaskFormState> get() = _formState

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

    override fun selectTask(task: Task?, showDialog: Boolean) {
        val curState = uiState.value
        if (curState.currentTask != task || curState.isInfoDialogShown != showDialog) {
            _uiState.value = uiState.value.copy(
                currentTask = task, isInfoDialogShown = task != null && showDialog
            )
        }
    }

    override fun updateForm(title: String, description: String) {
        _formState.value = formState.value.copy(
            title = title, description = description
        )
    }

    override fun createTask(onSuccess: (Status, String) -> Unit, onError: () -> Unit) {
        val (title, description) = formState.value

        _formState.value = formState.value.copy(hasError = false)

        createTaskUseCase(CreateTaskUseCase.Params(title, description))
            .watch { result ->
                viewModelScope.launch {
                    if (result.isSuccess) {
                        onSuccess(Status.SUCCEED_CREATE, result.get().id)
                    } else {
                        _formState.emit(formState.value.copy(hasError = true))
                        onError()
                    }
                }
            }
    }

    override fun clearFormState() {
        _formState.value = TaskFormState()
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

class TaskViewModelStub(
    tasks: List<Task>? = null,
    currentTask: Task? = null,
) : TaskViewModel() {
    override val uiState: StateFlow<TasksUiState> = MutableStateFlow(
        TasksUiState(
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
    override val formState: StateFlow<TaskFormState> = MutableStateFlow(
        TaskFormState("This is the title", "And this is the description of the titled task")
    )

    override var completingTask: Task? = null
    override fun beginRoutine(context: Context) = Unit
    override fun completeTask(fileBytes: ByteArray, context: Context) = Unit
    override fun changeFilter(newOption: FilterOption) = Unit
    override fun selectTask(task: Task?, showDialog: Boolean) = Unit
    override fun updateForm(title: String, description: String) = Unit
    override fun createTask(onSuccess: (Status, String) -> Unit, onError: () -> Unit) = Unit
    override fun clearFormState() = Unit
}

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilterOption: FilterOption = FilterOption.ALL,
    val currentTask: Task? = null,
    val isInfoDialogShown: Boolean = false,
)

data class TaskFormState(
    val title: String = "",
    val description: String = "",
    val hasError: Boolean = false
) {
    val isCompleted get() = title.isNotBlank() && description.isNotBlank()
}
