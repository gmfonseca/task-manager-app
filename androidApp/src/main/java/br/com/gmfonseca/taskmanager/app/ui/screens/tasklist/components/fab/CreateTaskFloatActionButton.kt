package br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.fab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color

@Composable
fun CreateTaskFloatActionButton(onClick: () -> Unit, hasTasks: Boolean) {
    FloatingActionButton(onClick = onClick, backgroundColor = Color.Yellow2) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Filled.Add, contentDescription = "Add", modifier = Modifier.size(24.dp))

            if (!hasTasks) {
                Text(text = "CREATE", fontSize = 16.sp, modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}

@Preview
@Composable
private fun TaskListFloatActionButtonPreview() {
    CreateTaskFloatActionButton({}, hasTasks = false)
}
