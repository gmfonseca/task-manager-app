package br.com.gmfonseca.taskmanager.app.ui.screens.task.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.TasksListHeader
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.fab.CreateTaskFloatActionButton
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskdialog.TaskDetailsDialog
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskslist.TasksList
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TasksListScreen(taskViewModel: TaskViewModel, onTaskCardClick: (Task) -> Unit, onFabClicked: () -> Unit) {
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
        floatingActionButton = {
            CreateTaskFloatActionButton(
                onFabClicked,
                uiState.tasks.isNotEmpty()
            )
        }
    ) {
        TasksList(
            uiState.tasks,
            uiState.selectedFilterOption,
            onTaskCardClick = onTaskCardClick,
            onInfoClick = { taskViewModel.selectTask(it, showDialog = true) }
        )

        if (uiState.isInfoDialogShown) {
            uiState.currentTask?.let {
                TaskDetailsDialog(it, onDismiss = { taskViewModel.selectTask(null) })
            }
        }
    }
}

@Preview
@Composable
private fun TasksListPreview() {
    TasksListScreen(TaskViewModelStub(), {}, {})
}
