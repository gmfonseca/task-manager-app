package br.com.gmfonseca.taskmanager.app.ui.task.screens.create.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color

@Composable
fun CreateTaskFormHeader(onBackPress: () -> Unit) {
    Box(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = onBackPress,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            elevation = null,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Go back",
                Modifier.size(24.dp)
            )
        }

        Text(
            text = "Create Task",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun CreateTaskFormHeaderPreview() {
    CreateTaskFormHeader {}
}
