package br.com.gmfonseca.taskmanager.app.ui.screens.task.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import br.com.gmfonseca.taskmanager.app.ui.components.ConfirmButton
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotification
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.components.input.LabeledTextField
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.components.CreateTaskFormHeader

@Composable
fun CreateTaskFormScreen(
    taskViewModel: TaskViewModel,
    onBackPress: () -> Unit,
    onCreatePress: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val formState by taskViewModel.formState.collectAsState()

    BackHandler(true, onBackPress)

    if (formState.hasError) {
        LaunchedEffect(scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar("Failed to create the task")
        }
    }

    Scaffold(
        backgroundColor = Color.Gray1,
        topBar = { CreateTaskFormHeader(onBackPress) },
        snackbarHost = {
            SnackbarHost(scaffoldState.snackbarHostState) {
                SnackbarNotification(
                    data = SnackbarNotificationData.Failure(it.message),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        },
        bottomBar = {
            ConfirmButton(
                onClick = onCreatePress,
                text = "CREATE TASK",
                enabled = formState.isCompleted
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            LabeledTextField(
                label = "TITLE",
                value = formState.title,
                onValueChange = { taskViewModel.updateForm(title = it) },
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                isRequired = true,
            )

            LabeledTextField(
                label = "DESCRIPTION",
                value = formState.description,
                onValueChange = { taskViewModel.updateForm(description = it) },
                modifier = Modifier.padding(top = 4.dp),
                isRequired = true,
            )
        }
    }
}

@Preview
@Composable
private fun CreateTaskFormScreenPreview() {
    CreateTaskFormScreen(TaskViewModelStub(), {}, {})
}
