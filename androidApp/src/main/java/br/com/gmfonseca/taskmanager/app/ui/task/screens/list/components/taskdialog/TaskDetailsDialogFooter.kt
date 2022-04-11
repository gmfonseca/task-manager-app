package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.taskdialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption

@Composable
fun TaskDetailsDialogFooter(isCompleted: Boolean) {
    val status = if (isCompleted) FilterOption.DONE else FilterOption.PENDING

    Row(Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(id = R.string.tasks_list_dialog_status),
            fontSize = 12.sp,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = stringResource(id = status.textRes),
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
