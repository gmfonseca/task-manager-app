package br.com.gmfonseca.taskmanager.app.ui.screens.task.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.TasksUiState
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotification
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.TasksListHeader
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.fab.CreateTaskFloatActionButton
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskdialog.TaskDetailsDialog
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskslist.TasksList
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TasksListScreen(
    taskViewModel: TaskViewModel,
    onTaskCardClick: (Task) -> Unit,
    onFabClick: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val uiState by taskViewModel.uiState.collectAsState()

    uiState.snackbarData?.let {
        LaunchedEffect(uiState.snackbarData) {
            scaffoldState.snackbarHostState.showSnackbar("")
        }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose(taskViewModel::clearListSnackbarState)
    }

    TasksListScreenContent(
        uiState = uiState,
        scaffoldState = scaffoldState,
        onFabClick = onFabClick,
        onTaskCardClick = onTaskCardClick,
        changeFilter = taskViewModel::changeFilter,
        onInfoClick = { taskViewModel.selectTask(it, showDialog = true) },
        onDialogDismiss = { taskViewModel.selectTask(null) },
    )
}

@Composable
private fun TasksListScreenContent(
    uiState: TasksUiState,
    scaffoldState: ScaffoldState,
    onFabClick: () -> Unit,
    onDialogDismiss: () -> Unit,
    onInfoClick: (Task) -> Unit,
    onTaskCardClick: (Task) -> Unit,
    changeFilter: (FilterOption) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Gray1,
        topBar = {
            TasksListHeader(
                title = "Tasks",
                selectedFilterOption = uiState.selectedFilterOption,
                onFilterChanged = changeFilter,
                Modifier.padding(top = 16.dp)
            )
        },
        floatingActionButton = {
            CreateTaskFloatActionButton(
                onFabClick,
                uiState.tasks.isNotEmpty()
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = it, modifier = Modifier.padding(bottom = 8.dp)) {
                uiState.snackbarData?.run { SnackbarNotification(data = this) }
            }
        },
    ) {
        TasksList(
            uiState.tasks,
            uiState.selectedFilterOption,
            onTaskCardClick = onTaskCardClick,
            onInfoClick = onInfoClick
        )

        if (uiState.isInfoDialogShown) {
            uiState.currentTask?.let {
                TaskDetailsDialog(it, onDismiss = onDialogDismiss)
            }
        }
    }
}

@Preview
@Composable
private fun TasksListPreview() {
    TasksListScreen(TaskViewModelStub(), {}, {})
}
