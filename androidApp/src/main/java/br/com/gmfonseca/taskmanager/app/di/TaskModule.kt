package br.com.gmfonseca.taskmanager.app.di

import br.com.gmfonseca.taskmanager.app.ui.TaskViewModel
import br.com.gmfonseca.taskmanager.app.ui.TaskViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun taskModule() = module {
    viewModel<TaskViewModel> { TaskViewModelImpl() }
}