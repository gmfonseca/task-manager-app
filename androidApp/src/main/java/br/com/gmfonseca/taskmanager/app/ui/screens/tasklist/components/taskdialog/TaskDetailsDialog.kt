package br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.taskdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TaskDetailsDialog(task: Task, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        TaskDetailsDialogContent(task = task, onDismiss = onDismiss)
    }
}

@Composable
private fun TaskDetailsDialogContent(task: Task, onDismiss: () -> Unit) {
    Column(
        Modifier
            .background(Color.Gray1, shape = RoundedCornerShape(8.dp))
            .padding(start = 16.dp, bottom = 16.dp, top = 8.dp, end = 8.dp),
    ) {
        TaskDetailsDialogHeader(onDismiss, task.id)

        Text(
            text = task.title,
            modifier = Modifier.padding(end = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        Text(
            text = task.description,
            modifier = Modifier.padding(top = 4.dp),
            fontSize = 12.sp,
        )

        TaskDetailsDialogFooter(task.isCompleted)
    }
}

@Preview
@Composable
private fun TaskDetailsDialogContentPreview() {
    TaskDetailsDialogContent(
        task = Task(
            id = "1",
            title = "This is a cool task title",
            description = "This is a cool task description",
        ),
    ) {}
}
