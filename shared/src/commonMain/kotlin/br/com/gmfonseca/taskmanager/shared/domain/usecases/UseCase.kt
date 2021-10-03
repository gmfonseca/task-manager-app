package br.com.gmfonseca.taskmanager.shared.domain.usecases

interface UseCase<in Params : UseCase.Params, out Result> {
    operator fun invoke(params: Params): Result

    sealed class Params
}

object None : UseCase.Params()
