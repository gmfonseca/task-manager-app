package br.com.gmfonseca.taskmanager.shared.common

sealed class Result<T> {

    data class Success<T> internal constructor(internal val data: T) : Result<T>()
    data class Failure<T> internal constructor(internal val exception: Throwable) : Result<T>()

    val isSuccess get() = this is Success
    val isFailure get() = this is Failure

    @Throws(IllegalArgumentException::class)
    fun get() = requireNotNull(getOrNull()) { "The result was null" }

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
        fun <T> failure(exception: Throwable): Failure<T> {
            println(exception)
            return Failure(exception)
        }
    }
}

fun <T, R, C : Collection<T>> Result<C>.map(transform: (T) -> R): Result<List<R>> {
    return when (this) {
        is Result.Failure -> {
            Result.failure(exception)
        }

        is Result.Success -> {
            Result.success(data.map { transform(it) })
        }
    }
}

fun <T, R, C : MutableCollection<in R>> Result<Collection<T>>.mapTo(
    destination: C,
    transform: (T) -> R
): Result<C> {
    return when (this) {
        is Result.Failure -> {
            Result.failure(exception)
        }

        is Result.Success -> {
            Result.success(data.mapTo(destination) { transform(it) })
        }
    }
}
