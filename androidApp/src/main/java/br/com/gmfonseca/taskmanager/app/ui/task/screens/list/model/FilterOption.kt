package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model

import androidx.annotation.StringRes
import br.com.gmfonseca.taskmanager.R

enum class FilterOption(@StringRes val textRes: Int) {
    ALL(R.string.tasks_list_filter_all),
    PENDING(R.string.tasks_list_filter_pending),
    DONE(R.string.tasks_list_filter_done)
}
