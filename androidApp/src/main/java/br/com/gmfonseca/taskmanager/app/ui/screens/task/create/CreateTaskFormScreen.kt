package br.com.gmfonseca.taskmanager.app.ui.screens.task.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.components.ConfirmButton
import br.com.gmfonseca.taskmanager.app.ui.components.input.LabeledTextField
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.components.CreateTaskFormHeader

@Composable
fun CreateTaskFormScreen(
    taskViewModel: TaskViewModel,
    onBackPress: () -> Unit,
    onCreatePress: () -> Unit,
) {
    val formState by taskViewModel.formState.collectAsState()

    Scaffold(
        backgroundColor = Color.Gray1,
        topBar = { CreateTaskFormHeader(onBackPress) },
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
