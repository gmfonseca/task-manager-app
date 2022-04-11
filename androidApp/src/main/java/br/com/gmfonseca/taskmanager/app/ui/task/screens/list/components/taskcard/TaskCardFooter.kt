package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.taskcard

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun TaskCardFooter(task: Task, onClick: (Task) -> Unit, cardColor: ComposeColor, enabled: Boolean) {
    val btnLabel = stringResource(
        id = if (task.isCompleted) {
            R.string.tasks_list_card_view_details_label
        } else {
            R.string.tasks_list_card_complete_label
        }
    )

    Divider(
        color = Color.Gray1,
        modifier = Modifier.padding(top = 8.dp)
    )

    Button(
        onClick = { onClick(task) },
        modifier = Modifier
            .fillMaxWidth()
            .widthIn()
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = cardColor,
            disabledBackgroundColor = cardColor,
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 4.dp),
        enabled = enabled
    ) {
        Text(
            text = btnLabel,
            color = Color.TextGray1,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = btnLabel,
            modifier = Modifier.size(16.dp),
            tint = Color.TextGray1,
        )
    }
}

@Preview
@Composable
private fun TaskCardFooterPreview() {
    TaskCardFooter(
        task = Task(
            id = "1",
            title = "This is a cool task title",
            description = "This is a cool task description",
        ),
        onClick = {},
        cardColor = Color.Gray4,
        enabled = false
    )
}
