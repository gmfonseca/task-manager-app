package br.com.gmfonseca.taskmanager.app.ui.task.screens.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.task.model.TaskFormUiState
import br.com.gmfonseca.taskmanager.app.ui.task.viewmodel.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.task.viewmodel.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.components.ConfirmButton
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotification
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.components.input.LabeledTextField
import br.com.gmfonseca.taskmanager.app.ui.task.screens.create.components.CreateTaskFormHeader

@Composable
fun CreateTaskFormScreen(
    taskViewModel: TaskViewModel,
    onBackPress: () -> Unit,
    onCreatePress: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val formUiState by taskViewModel.formUiState.collectAsState()

    BackHandler(onBack = onBackPress)

    if (formUiState.hasError) {
        LaunchedEffect(formUiState) {
            scaffoldState.snackbarHostState.showSnackbar("Failed to create the task")
        }
    }

    CreateTaskFormScreenContent(
        formUiState = formUiState,
        scaffoldState = scaffoldState,
        onBackPress = onBackPress,
        onCreatePress = onCreatePress,
        onTitleChange = { taskViewModel.updateForm(title = it) },
        onDescriptionChange = { taskViewModel.updateForm(description = it) },
    )
}

@Composable
private fun CreateTaskFormScreenContent(
    formUiState: TaskFormUiState,
    scaffoldState: ScaffoldState,
    onBackPress: () -> Unit,
    onCreatePress: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Gray1,
        topBar = { CreateTaskFormHeader(onBackPress) },
        snackbarHost = { hostState ->
            SnackbarHost(hostState) {
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
                enabled = formUiState.isCompleted
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
                value = formUiState.title,
                onValueChange = onTitleChange,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                singleLine = true,
                isRequired = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            LabeledTextField(
                label = "DESCRIPTION",
                value = formUiState.description,
                onValueChange = onDescriptionChange,
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
