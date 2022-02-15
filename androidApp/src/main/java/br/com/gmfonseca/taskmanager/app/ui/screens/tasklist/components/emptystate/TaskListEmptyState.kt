package br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.components.emptystate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.gmfonseca.taskmanager.app.ui.components.stateful.EmptyState
import br.com.gmfonseca.taskmanager.app.ui.components.stateful.EmptyStateProperties
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.model.FilterOption

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
    object : EmptyStateProperties {
        override val icon = Icons.Filled.LibraryBooks
        override val iconDescription = "library books"
        override val title = "No tasks available!"
        override val description =
            "Create a new task and\norganize your life in the pocket by\nusing the button below."
    }
}

private val PendingEmptyListProperties by lazy {
    object : EmptyStateProperties {
        override val icon = Icons.Filled.PendingActions
        override val iconDescription = "pending actions"
        override val title = "No pending tasks!"
        override val description =
            "Change the filter or\ncreate a new task by using\nthe button below."
    }
}

private val DoneEmptyListProperties by lazy {
    object : EmptyStateProperties {
        override val icon = Icons.Filled.Task
        override val iconDescription = "completed task"
        override val title = "No done tasks!"
        override val description =
            "Change the filter and\ncomplete a pending task.\n"
    }
}
