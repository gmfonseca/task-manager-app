package br.com.gmfonseca.taskmanager

interface UseCase<in Params : UseCase.Params, out Result> {
    operator fun invoke(param: Params): Result

    sealed class Params
}

object None : UseCase.Params()
