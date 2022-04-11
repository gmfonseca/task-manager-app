package br.com.gmfonseca.taskmanager.app.ui.components.feedback

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.app.core.design.Color
import androidx.compose.ui.graphics.Color as AndroidColor

@Composable
fun SnackbarNotification(
    data: SnackbarNotificationData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(32.dp)
            .background(data.color, shape = RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .padding(8.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            data.icon,
            contentDescription = data.contentDescription,
            tint = data.contentColor,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = stringResource(id = data.textRes, *data.textResArgs),
            color = data.contentColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Preview(name = "Failure", showBackground = true)
@Composable
private fun SnackbarNotificationFailurePreview() {
    SnackbarNotification(
        SnackbarNotificationData.Failure(textRes = R.string.tasks_list_snackbar_complete_task_failed)
    )
}

@Preview(name = "Success", showBackground = true)
@Composable
private fun SnackbarNotificationSuccessPreview() {
    SnackbarNotification(
        SnackbarNotificationData.Success(textRes = R.string.tasks_list_snackbar_complete_task_succeed)
    )
}

sealed class SnackbarNotificationData(
    @StringRes val textRes: Int,
    val color: AndroidColor,
    val contentColor: AndroidColor,
    val icon: ImageVector,
    val contentDescription: String? = null,
    val textResArgs: Array<out Any> = emptyArray(),
) {
    class Success(textRes: Int, vararg textResArgs: Any) : SnackbarNotificationData(
        textRes = textRes,
        color = Color.Green,
        contentColor = Color.White,
        icon = Icons.Default.CheckCircle,
        contentDescription = "Succeed",
        textResArgs = textResArgs
    )

    class Failure(textRes: Int, vararg textResArgs: Any) : SnackbarNotificationData(
        textRes = textRes,
        color = Color.Red,
        contentColor = Color.White,
        icon = Icons.Default.Error,
        contentDescription = "Failed",
        textResArgs = textResArgs
    )
}
