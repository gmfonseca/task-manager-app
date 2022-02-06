package br.com.gmfonseca.taskmanager.shared.domain.usecases

interface UseCase<in Params : UseCase.Params, out Result> {
    operator fun invoke(params: Params): Result

    sealed interface Params
}

object None : UseCase.Params
