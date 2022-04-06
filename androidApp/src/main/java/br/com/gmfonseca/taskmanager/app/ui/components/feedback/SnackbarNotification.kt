package br.com.gmfonseca.taskmanager.app.ui.components.feedback

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import androidx.compose.ui.graphics.Color as AndroidColor

@Composable
fun SnackbarNotification(
    data: SnackbarNotificationData,
    modifier: Modifier = Modifier
) {
    Snackbar(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = data.color,
        modifier = Modifier
            .heightIn(max = 32.dp)
            .then(modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(data.icon, contentDescription = data.contentDescription)
            Text(
                text = data.text,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(name = "Failure", showBackground = true)
@Composable
private fun SnackbarNotificationFailurePreview() {
    SnackbarNotification(
        SnackbarNotificationData.Failure(text = "Failed to complete the task #1")
    )
}

@Preview(name = "Success", showBackground = true)
@Composable
private fun SnackbarNotificationSuccessPreview() {
    SnackbarNotification(
        SnackbarNotificationData.Success(text = "Failed to complete the task #1")
    )
}

sealed class SnackbarNotificationData(
    val text: String,
    val color: AndroidColor,
    val icon: ImageVector,
    val contentDescription: String? = null,
) {
    class Success(text: String) :
        SnackbarNotificationData(text, Color.Green, Icons.Default.CheckCircle, "Succeed")

    class Failure(text: String) :
        SnackbarNotificationData(text, Color.Red, Icons.Default.Error, "Failed")
}
