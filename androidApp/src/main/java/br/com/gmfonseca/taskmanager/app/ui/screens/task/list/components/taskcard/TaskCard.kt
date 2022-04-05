package br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TaskCard(task: Task, onInfoClick: () -> Unit, onClick: (Task) -> Unit, enabled: Boolean) {
    val cardColor = if (task.isCompleted) Color.Gray4 else Color.Gray3

    Card(
        modifier = Modifier
            .padding(8.dp)
            .heightIn(min = 120.dp)
            .fillMaxWidth(),
        backgroundColor = cardColor,
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            TaskCardHeader(task, onInfoClick, cardColor)

            TaskCardContent(task)

            TaskCardFooter(task, onClick, cardColor, enabled)
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    TaskCard(
        task = Task(
            id = "1",
            title = "This is a cool task title",
            description = "This is a cool task description",
        ),
        onInfoClick = {},
        onClick = {},
        enabled = false
    )
}