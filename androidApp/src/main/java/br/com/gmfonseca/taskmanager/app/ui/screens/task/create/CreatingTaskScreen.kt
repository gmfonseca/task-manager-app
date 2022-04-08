package br.com.gmfonseca.taskmanager.app.ui.screens.task.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color

@Composable
fun CreatingTaskScreen() {
    BackHandler(true) {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = Color.Yellow2,
            modifier = Modifier.size(108.dp)
        )
        Text(
            text = "Creating task...", fontSize = 20.sp, color = Color.TextGray2,
            modifier =
            Modifier.padding(top = 16.dp),
        )
    }
}

@Preview
@Composable
private fun CreatingTaskScreenPreview() {
    CreatingTaskScreen()
}
