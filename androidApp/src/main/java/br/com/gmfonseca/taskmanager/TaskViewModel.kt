package br.com.gmfonseca.taskmanager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gmfonseca.taskmanager.shared.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private lateinit var INSTANCE: TaskViewModel

interface TaskViewModel {
    val tasksState: StateFlow<List<Task>>

    fun beginRoutine(context: Context)
}

class TaskViewModelImpl private constructor() : ViewModel(), TaskViewModel {
    private val fetchRemoteTasks by lazy { FetchRemoteTasksRoutineUseCaseImpl() }

    private val _tasksState = MutableStateFlow<List<Task>>(emptyList())
    override val tasksState: StateFlow<List<Task>> get() = _tasksState

    override fun beginRoutine(context: Context) {
        viewModelScope.launch {
            fetchRemoteTasks(None).collect {
                _tasksState.value = it.get()
            }
        }
    }

    companion object {
        operator fun invoke(): TaskViewModel {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = TaskViewModelImpl()
            }

            return INSTANCE
        }
    }
}
