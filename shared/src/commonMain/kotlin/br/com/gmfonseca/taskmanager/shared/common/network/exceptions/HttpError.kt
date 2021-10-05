package br.com.gmfonseca.taskmanager.shared.common.network.exceptions

sealed class HttpError(val code: Int) : Throwable()
