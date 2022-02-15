package br.com.gmfonseca.taskmanager.app.ui.screens.tasklist

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TasksUiState
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.TasksListHeader
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.taskdialog.TaskDetailsDialog
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.taskslist.TasksList
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TasksListScreen(taskViewModel: TaskViewModel, onTaskCardClick: (Task) -> Unit) {
    val uiState by taskViewModel.uiState.collectAsState()

    Scaffold(
        backgroundColor = Color.Gray1,
        topBar = {
            TasksListHeader(
                title = "Tasks",
                selectedFilterOption = uiState.selectedFilterOption,
                onFilterChanged = taskViewModel::changeFilter,
            )
        },
    ) {
        TasksList(
            uiState.tasks,
            uiState.selectedFilterOption,
            onTaskCardClick = onTaskCardClick,
            onInfoClick = taskViewModel::selectTask
        )

        uiState.currentTask?.let {
            TaskDetailsDialog(it, onDismiss = { taskViewModel.selectTask(null) })
        }
    }
}

@Preview
@Composable
private fun TasksListPreview() {
    TasksListScreen(TaskViewModelStub()) {}
}

private class TaskViewModelStub(
    tasks: List<Task>? = null
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
            )
        )
    )

    override var completingTask: Task? = null
    override fun beginRoutine(context: Context) = Unit
    override fun completeTask(fileBytes: ByteArray, context: Context) = Unit
    override fun changeFilter(newOption: FilterOption) = Unit
    override fun selectTask(task: Task?) = Unit
}
