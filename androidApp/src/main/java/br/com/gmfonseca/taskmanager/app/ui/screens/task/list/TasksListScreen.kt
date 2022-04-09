package br.com.gmfonseca.taskmanager.app.ui.screens.task.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotification
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.TasksListHeader
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.fab.CreateTaskFloatActionButton
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskdialog.TaskDetailsDialog
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskslist.TasksList
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TasksListScreen(
    taskViewModel: TaskViewModel,
    onTaskCardClick: (Task) -> Unit,
    onFabClicked: () -> Unit,
    snackbarData: SnackbarNotificationData?,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by taskViewModel.uiState.collectAsState()

    snackbarData?.let {
        LaunchedEffect(scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(it.text)
        }
    }

    Scaffold(
        backgroundColor = Color.Gray1,
        topBar = {
            TasksListHeader(
                title = "Tasks",
                selectedFilterOption = uiState.selectedFilterOption,
                onFilterChanged = taskViewModel::changeFilter,
                Modifier.padding(top = 16.dp)
            )
        },
        floatingActionButton = {
            CreateTaskFloatActionButton(
                onFabClicked,
                uiState.tasks.isNotEmpty()
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                snackbarData?.let { SnackbarNotification(data = it) }
            }
        },
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
    TasksListScreen(TaskViewModelStub(), {}, {}, null)
}
