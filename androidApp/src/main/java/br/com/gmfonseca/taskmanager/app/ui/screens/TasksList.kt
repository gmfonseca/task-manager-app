package br.com.gmfonseca.taskmanager.app.ui.screens

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.components.TaskCard
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TasksList(taskViewModel: TaskViewModel, onClick: (Task) -> Unit) {
    val tasks by taskViewModel.tasksState.collectAsState()

    Scaffold(backgroundColor = Color(0xFFFFFFFF)) {
        LazyColumn {
            items(tasks, itemContent = { task -> TaskCard(task = task, onClick = onClick) })
        }
    }
}

@Preview
@Composable
fun TasksListPreview() {
    TasksList(TaskViewModelStub()) {}
}

private class TaskViewModelStub(
    tasks: List<Task>? = null
) : TaskViewModel() {
    override val tasksState: StateFlow<List<Task>> = MutableStateFlow(
        value = tasks ?: listOf(
            Task(
                id = "1",
                title = "First task title",
                description = "First task description",
            ),
            Task(
                id = "2",
                title = "Second cool task title",
                description = "Second cool task description too big that can't fill the window",
            ),
        )
    )

    override var currentTask: Task? = TODO()
    override fun beginRoutine(context: Context) = TODO()
    override fun completeTask(fileBytes: ByteArray, context: Context) = TODO()
}