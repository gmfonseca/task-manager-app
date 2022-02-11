package br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.tasksfilter.TasksListFilter
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.model.FilterOption

@Composable
fun TasksListHeader(
    title: String,
    selectedFilterOption: FilterOption,
    onFilterChanged: (FilterOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        )
        TasksListFilter(
            selectedOption = selectedFilterOption,
            onOptionChanged = onFilterChanged,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun TaskListHeaderPreview() {
    TasksListHeader("Title", FilterOption.ALL, {})
}
