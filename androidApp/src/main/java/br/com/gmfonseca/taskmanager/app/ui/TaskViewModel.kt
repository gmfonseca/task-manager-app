package br.com.gmfonseca.taskmanager.app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTasksUseCase
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTasksUseCaseImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.FetchRemoteTasksRoutineUseCaseImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.None
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel() {
    abstract val tasksState: StateFlow<List<Task>>
    abstract var currentTask: Task?

    abstract fun beginRoutine(context: Context)
    abstract fun completeTask(fileBytes: ByteArray, context: Context)
}

class TaskViewModelImpl : TaskViewModel() {
    private val fetchRemoteTasksRoutine by lazy { FetchRemoteTasksRoutineUseCaseImpl() }
    private val completeTasksRoutine by lazy { CompleteTasksUseCaseImpl() }

    private val _tasksState = MutableStateFlow<List<Task>>(emptyList())
    override val tasksState: StateFlow<List<Task>> get() = _tasksState

    override var currentTask: Task? = null

    private var hasActiveRoutine = false

    override fun beginRoutine(context: Context) {
        if (!hasActiveRoutine) {
            hasActiveRoutine = true

            fetchRemoteTasksRoutine(None).watch { _tasksState.value = it.get() }
        }
    }

    override fun completeTask(fileBytes: ByteArray, context: Context) {
        completeTasksRoutine(
            CompleteTasksUseCase.Params("${currentTask?.id}", fileBytes)
        ).watch { result ->
            if (result.isSuccess && result.get()) {
                viewModelScope.launch {
                    _tasksState.emit(_tasksState.value.filter { it != currentTask })
                }
            }
        }
    }
}
