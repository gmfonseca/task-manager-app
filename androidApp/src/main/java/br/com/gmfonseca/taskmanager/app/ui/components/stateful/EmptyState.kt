package br.com.gmfonseca.taskmanager.app.ui.components.stateful

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyState(properties: EmptyStateProperties) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Icon(properties.icon, contentDescription = properties.iconDescription, Modifier.size(64.dp))
        Text(
            text = properties.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = properties.description,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    EmptyState(
        object : EmptyStateProperties {
            override val icon = Icons.Default.Done
            override val iconDescription = "email"
            override val title = "No tasks available!"
            override val description =
                "Create a new task and\norganize your life in the pocket by\nusing the button below."
        }
    )
}

interface EmptyStateProperties {
    val icon: ImageVector
    val iconDescription: String
    val title: String
    val description: String
}
