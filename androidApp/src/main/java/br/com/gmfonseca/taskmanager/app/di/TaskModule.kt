package br.com.gmfonseca.taskmanager.app.di

import br.com.gmfonseca.taskmanager.app.ui.task.viewmodel.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.task.viewmodel.TaskViewModelImpl
import br.com.gmfonseca.taskmanager.shared.domain.usecases.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun taskModule() = module {
    factory<FetchRemoteTasksRoutineUseCase> { FetchRemoteTasksRoutineUseCaseImpl() }
    factory<CompleteTaskUseCase> { CompleteTaskUseCaseImpl() }
    factory<CreateTaskUseCase> { CreateTaskUseCaseImpl() }

    viewModel<TaskViewModel> { TaskViewModelImpl(get(), get(), get()) }
}
