package br.com.gmfonseca.taskmanager.app.ui.task.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.app.ui.task.viewmodel.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.task.viewmodel.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.task.model.TasksListUiState
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotification
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.TasksListHeader
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.fab.CreateTaskFloatActionButton
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.taskdialog.TaskDetailsDialog
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.taskslist.TasksList
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TasksListScreen(
    taskViewModel: TaskViewModel,
    onTaskCardClick: (Task) -> Unit,
    onFabClick: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val listUiState by taskViewModel.listUiState.collectAsState()

    listUiState.snackbarData?.let {
        LaunchedEffect(listUiState.snackbarData) {
            scaffoldState.snackbarHostState.showSnackbar("")
        }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose(taskViewModel::clearListSnackbarState)
    }

    TasksListScreenContent(
        listUiState = listUiState,
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
    listUiState: TasksListUiState,
    scaffoldState: ScaffoldState,
    onFabClick: () -> Unit,
    onDialogDismiss: () -> Unit,
    onInfoClick: (Task) -> Unit,
    onTaskCardClick: (Task) -> Unit,
    changeFilter: (FilterOption) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TasksListHeader(
                title = stringResource(id = R.string.tasks_list_title),
                selectedFilterOption = listUiState.selectedFilterOption,
                onFilterChanged = changeFilter,
                Modifier.padding(top = 16.dp)
            )
        },
        floatingActionButton = {
            CreateTaskFloatActionButton(
                onClick = onFabClick,
                hasTasks = listUiState.tasks.isNotEmpty(),
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = it, modifier = Modifier.padding(bottom = 8.dp)) {
                listUiState.snackbarData?.run { SnackbarNotification(data = this) }
            }
        },
    ) {
        TasksList(
            listUiState.tasks,
            listUiState.selectedFilterOption,
            onTaskCardClick = onTaskCardClick,
            onInfoClick = onInfoClick
        )

        if (listUiState.isInfoDialogShown) {
            listUiState.currentTask?.let {
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
