package br.com.gmfonseca.taskmanager.app

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.gmfonseca.taskmanager.shared.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TasksList(taskViewModel: TaskViewModel, onClick: (Task) -> Unit) {
    val tasks by taskViewModel.tasksState.collectAsState()

    Scaffold {
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

@Composable
fun TaskCard(task: Task, onClick: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        backgroundColor = Color(red = 0f, green = 0f, blue = 0f, alpha = 0.27f)
    ) {
        ConstraintLayout {
            val (container, button) = createRefs()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.constrainAs(container) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(button.start)
                    width = Dimension.fillToConstraints
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.DarkGray,
                    )
                    Text(
                        text = task.description,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.DarkGray
                    )
                }
            }

            Button(
                onClick = { onClick(task) },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .wrapContentWidth()
                    .constrainAs(button) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Text(text = "COMPLETE", color = Color.DarkGray)
            }
        }
    }
}

//@Preview
@Composable
fun TaskCardPreview() {
    TaskCard(
        task = Task(
            id = "1",
            title = "This is a cool task title",
            description = "This is a cool task description",
        ),
        onClick = {}
    )
}

private class TaskViewModelStub(
    tasks: List<Task>? = null
) : TaskViewModel {
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
    override var currentTask: Task?
        get() = TODO("Not yet implemented")
        set(_) {}

    override fun beginRoutine(context: Context) = TODO()
    override fun completeTask(fileBytes: ByteArray, context: Context) =
        TODO("Not yet implemented")
}
