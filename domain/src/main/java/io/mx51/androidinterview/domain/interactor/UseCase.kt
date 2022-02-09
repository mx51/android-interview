package io.mx51.androidinterview.domain.interactor

abstract class UseCase<in UseCaseParams, out UseCaseResult> {

    abstract suspend fun run(params: UseCaseParams): UseCaseResult

    suspend operator fun invoke(
        params: UseCaseParams
    ): UseCaseResult {
        return run(params)
    }
}