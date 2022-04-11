package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.taskdialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color

@Composable
fun TaskDetailsDialogHeader(onDismiss: () -> Unit, taskId: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "#${taskId}",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )

        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            elevation = null,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .widthIn(max = 32.dp)
                .heightIn(max = 32.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close dialog", Modifier.size(16.dp))
        }
    }
}

@Preview
@Composable
fun TaskDetailsDialogHeaderPreview() {
    TaskDetailsDialogHeader(onDismiss = {}, taskId = "1")
}
