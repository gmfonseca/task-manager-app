package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.fab

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.R

@Composable
fun CreateTaskFloatActionButton(
    onClick: () -> Unit,
    hasTasks: Boolean
) {
    FloatingActionButton(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Filled.Add, contentDescription = "Add", modifier = Modifier.size(24.dp))

            AnimatedVisibility(visible = !hasTasks) {
                Text(
                    text = stringResource(id = R.string.tasks_list_fab_create),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskListFloatActionButtonPreview() {
    CreateTaskFloatActionButton({}, hasTasks = false)
}
