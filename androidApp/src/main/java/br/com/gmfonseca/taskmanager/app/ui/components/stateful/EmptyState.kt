package br.com.gmfonseca.taskmanager.app.ui.components.stateful

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.R

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
            text = stringResource(id = properties.title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = properties.description),
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
        EmptyStateProperties(
            icon = Icons.Default.Done,
            iconDescription = "email",
            title = R.string.tasks_list_empty_state_section_all_title,
            description = R.string.tasks_list_empty_state_section_all_description
        )
    )
}

data class EmptyStateProperties(
    val icon: ImageVector,
    val iconDescription: String,
    @StringRes val title: Int,
    @StringRes val description: Int
)
