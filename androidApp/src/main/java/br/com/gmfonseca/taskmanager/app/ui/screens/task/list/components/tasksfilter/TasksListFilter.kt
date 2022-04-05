package br.com.gmfonseca.taskmanager.app.ui.screens.task.list.components.tasksfilter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.model.FilterOption

@Composable
fun TasksListFilter(
    selectedOption: FilterOption,
    onOptionChanged: (FilterOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    fun isSelectedOption(option: FilterOption) = selectedOption == option

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val optionsModifier = Modifier.weight(1f)

        FilterOption(
            option = FilterOption.ALL,
            isSelected = isSelectedOption(FilterOption.ALL),
            onClick = onOptionChanged,
            modifier = optionsModifier,
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        )

        FilterOption(
            option = FilterOption.PENDING,
            isSelected = isSelectedOption(FilterOption.PENDING),
            onClick = onOptionChanged,
            modifier = optionsModifier
        )

        FilterOption(
            option = FilterOption.DONE,
            isSelected = isSelectedOption(FilterOption.DONE),
            onClick = onOptionChanged,
            modifier = optionsModifier,
            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        )
    }
}

@Preview("All")
@Composable
private fun TasksListFilterAllPreview() {
    TasksListFilter(FilterOption.ALL, {})
}

@Preview("Pending")
@Composable
private fun TasksListFilterPendingPreview() {
    TasksListFilter(FilterOption.PENDING, {})
}

@Preview("Done")
@Composable
private fun TasksListFilterDonePreview() {
    TasksListFilter(FilterOption.DONE, {})
}
