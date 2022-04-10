package br.com.gmfonseca.taskmanager.app.ui.screens.task.details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModelStub
import br.com.gmfonseca.taskmanager.app.ui.screens.task.details.components.TaskDetailsImage
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TaskDetailsScreen(taskViewModel: TaskViewModel, onBackPress: () -> Unit) {
    val listUiState by taskViewModel.listUiState.collectAsState()
    val task = listUiState.currentTask ?: return run {
        Toast.makeText(
            LocalContext.current,
            "Whoops! Something went wrong to open the task details.",
            Toast.LENGTH_SHORT
        ).show()

        onBackPress()
    }

    TaskDetailsScreenDetails(task = task, onBackPress = onBackPress)
}

@Composable
private fun TaskDetailsScreenDetails(task: Task, onBackPress: () -> Unit) {
    Scaffold(
        topBar = { TaskDetailsImage(taskId = task.id, onBackPress = onBackPress) }
    ) {
        Column {
            Text(
                text = task.title,
                color = Color.TextGray2,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )

            Text(
                text = task.description,
                color = Color.TextGray2,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TaskDetailsScreenPreview() {
    TaskDetailsScreen(
        TaskViewModelStub(
            currentTask = Task(
                id = "1",
                title = "First task title",
                description = "First task description",
            )
        ),
    ) {}
}
