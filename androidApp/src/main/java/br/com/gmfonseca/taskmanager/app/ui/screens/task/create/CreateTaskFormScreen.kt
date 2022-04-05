package br.com.gmfonseca.taskmanager.app.ui.screens.task.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.components.ConfirmButton
import br.com.gmfonseca.taskmanager.app.ui.components.input.LabeledTextField
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.components.CreateTaskFormHeader

@Composable
fun CreateTaskFormScreen(onBackPress: () -> Unit) {
    val (title, setTitle) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    Scaffold(
        backgroundColor = Color.Gray1,
        topBar = { CreateTaskFormHeader(onBackPress) },
        bottomBar = {
            ConfirmButton(onClick = {}, text = "CREATE TASK")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            LabeledTextField(
                label = "TITLE",
                value = title,
                onValueChange = setTitle,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            )

            LabeledTextField(
                label = "DESCRIPTION",
                value = description,
                onValueChange = setDescription,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}

@Preview
@Composable
private fun CreateTaskFormScreenPreview() {
    CreateTaskFormScreen({})
}
