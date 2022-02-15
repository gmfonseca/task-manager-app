package br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.taskslist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.emptystate.TaskListEmptyState
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.taskcard.TaskCard
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.model.FilterOption
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TasksList(
    tasks: List<Task>,
    selectedFilterOption: FilterOption,
    onTaskCardClick: (Task) -> Unit,
    onInfoClick: (Task) -> Unit
) {
    if (tasks.isEmpty()) {
        TaskListEmptyState(selectedFilterOption)
    } else {
        LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(
                key = Task::id,
                items = tasks,
                itemContent = { task ->
                    TaskCard(
                        task = task,
                        onClick = onTaskCardClick,
                        onInfoClick = { onInfoClick(task) },
                        enabled = true
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun TasksListPreview() {
    TasksList(emptyList(), FilterOption.ALL, {}, {})
}
