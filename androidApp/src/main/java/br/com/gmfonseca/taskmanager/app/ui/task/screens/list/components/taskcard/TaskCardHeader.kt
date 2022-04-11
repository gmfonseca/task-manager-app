package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.taskcard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun TaskCardHeader(task: Task, onInfoClick: () -> Unit, cardColor: ComposeColor) {
    val status = if (task.isCompleted) FilterOption.DONE else FilterOption.PENDING

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Text(
            text = "#${task.id}", modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onPrimary,
        )

        Button(
            onClick = onInfoClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = cardColor
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
            ),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            Text(
                text = stringResource(id = status.textRes).uppercase(),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp),
                color = MaterialTheme.colors.onPrimary,
            )
            Icon(
                Icons.Default.Info,
                contentDescription = "More info",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Preview
@Composable
private fun TaskCardHeaderPreview() {
    TaskCardHeader(
        task = Task(
            id = "1",
            title = "This is a cool task title",
            description = "This is a cool task description",
        ),
        onInfoClick = {},
        cardColor = MaterialTheme.colors.primary
    )
}
