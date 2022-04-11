package br.com.gmfonseca.taskmanager.app.ui.task.viewmodel

import android.content.Context
import br.com.gmfonseca.taskmanager.app.ui.task.model.TaskFormUiState
import br.com.gmfonseca.taskmanager.app.ui.task.model.TasksListUiState
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
