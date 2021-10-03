package br.com.gmfonseca.taskmanager.app

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import br.com.gmfonseca.taskmanager.shared.domain.model.Task
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTasksRoutineUseCase
import br.com.gmfonseca.taskmanager.shared.domain.usecases.CompleteTasksRoutineUseCaseImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.FetchRemoteTasksRoutineUseCaseImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.None
import br.com.gmfonseca.taskmanager.shared.utils.ext.watch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private lateinit var INSTANCE: TaskViewModel

interface TaskViewModel {
    val tasksState: StateFlow<List<Task>>
    var currentTask: Task?

    fun beginRoutine(context: Context)
    fun completeTask(fileBytes: ByteArray, context: Context)
}

class TaskViewModelImpl private constructor() : ViewModel(), TaskViewModel {
    private val fetchRemoteTasksRoutine by lazy { FetchRemoteTasksRoutineUseCaseImpl() }
    private val completeTasksRoutine by lazy { CompleteTasksRoutineUseCaseImpl() }

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
            CompleteTasksRoutineUseCase.Params("${currentTask?.id}", fileBytes)
        ).watch { Toast.makeText(context, "Complete task result: $it", Toast.LENGTH_LONG).show() }
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
