package br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.taskdialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskDetailsDialogFooter(isCompleted: Boolean) {
    Row(Modifier.padding(top = 16.dp)) {
        Text(
            text = "Status: ",
            fontSize = 12.sp,
        )
        Text(
            text = if (isCompleted) "DONE" else "PENDING",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }
}

@Preview
@Composable
fun TaskDetailsDialogFooterPreview() {
    TaskDetailsDialogHeader(onDismiss = {}, taskId = "1")
}
