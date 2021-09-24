package br.com.gmfonseca.taskmanager.shared.contract

sealed class Result<T> {

    data class Success<T> internal constructor(internal val data: T) : Result<T>()
    data class Failure<T> internal constructor(internal val exception: Throwable) : Result<T>()

    val isSuccess get() = this is Success
    val isFailure get() = this is Failure

    fun getOrNull() = if (this is Success) {
        data
    } else {
        null
    }

    fun exceptionOrNull() = if (this is Failure) {
        exception
    } else {
        null
    }

    companion object {
        fun <T> success(value: T) = Success(value)
        fun <T> failure(exception: Throwable) = Failure<T>(exception)
    }
}