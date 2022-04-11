package br.com.gmfonseca.taskmanager.app.ui.task.screens.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.R
import kotlinx.coroutines.delay

@Composable
fun CreatingTaskScreen() {
    BackHandler(onBack = {})

    LaunchedEffect(key1 = null) {
        delay(1_000L)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.size(108.dp)
        )

        Text(
            text = stringResource(id = R.string.creating_task_loading_text),
            fontSize = 20.sp,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Preview
@Composable
private fun CreatingTaskScreenPreview() {
    CreatingTaskScreen()
}
