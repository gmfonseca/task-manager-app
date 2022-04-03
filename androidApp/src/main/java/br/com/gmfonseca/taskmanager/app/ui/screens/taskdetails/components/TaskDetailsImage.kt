package br.com.gmfonseca.taskmanager.app.ui.screens.taskdetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.shared.common.Constants
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import coil.compose.rememberAsyncImagePainter

@Composable
fun TaskDetailsImage(task: Task, onBackPress: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(Color.Yellow1)
            .background(
                Brush.verticalGradient(listOf(Color.Gray1, Color.Gray5)),
                alpha = .2f
            )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(
                "${Constants.SERVICE_URL}/tasks/${task.id}/image",
                error = painterResource(id = R.drawable.ic_close)

            ),
            contentDescription = null,
        )

        FloatingActionButton(
            onClick = onBackPress,
            modifier = Modifier.padding(16.dp),
            backgroundColor = Color.White.copy(alpha = .75f),
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 8.dp)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Go back",
                Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TaskDetailsImagePreview() {
    TaskDetailsImage(
        task = Task(
            id = "1",
            title = "First task title",
            description = "First task description",
        ),
        onBackPress = {}
    )
}
