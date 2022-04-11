package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.emptystate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.app.ui.components.stateful.EmptyState
import br.com.gmfonseca.taskmanager.app.ui.components.stateful.EmptyStateProperties
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption

@Composable
fun TaskListEmptyState(filterOption: FilterOption) {
    EmptyState(
        properties = when (filterOption) {
            FilterOption.ALL -> AllEmptyListProperties
            FilterOption.PENDING -> PendingEmptyListProperties
            FilterOption.DONE -> DoneEmptyListProperties
        }
    )
}

@Preview(showBackground = true, name = "All")
@Composable
private fun TaskListAllEmptyStatePreview() {
    Column(Modifier.fillMaxSize()) {
        TaskListEmptyState(FilterOption.ALL)
    }
}

@Preview(showBackground = true, name = "Pending")
@Composable
private fun TaskListPendingEmptyStatePreview() {
    Column(Modifier.fillMaxSize()) {
        TaskListEmptyState(FilterOption.PENDING)
    }
}

@Preview(showBackground = true, name = "Done")
@Composable
private fun TaskListDoneEmptyStatePreview() {
    Column(Modifier.fillMaxSize()) {
        TaskListEmptyState(FilterOption.DONE)
    }
}

private val AllEmptyListProperties by lazy {
    EmptyStateProperties(
        icon = Icons.Filled.LibraryBooks,
        iconDescription = "library books",
        title = R.string.tasks_list_empty_state_section_all_title,
        description = R.string.tasks_list_empty_state_section_all_description
    )
}

private val PendingEmptyListProperties by lazy {
    EmptyStateProperties(
        icon = Icons.Filled.PendingActions,
        iconDescription = "pending actions",
        title = R.string.tasks_list_empty_state_section_pending_title,
        description = R.string.tasks_list_empty_state_section_pending_description,
    )
}

private val DoneEmptyListProperties by lazy {
    EmptyStateProperties(
        icon = Icons.Filled.Task,
        iconDescription = "task",
        title = R.string.tasks_list_empty_state_section_done_title,
        description = R.string.tasks_list_empty_state_section_done_description,
    )
}
