package br.com.knowledge.core

import kotlinx.coroutines.flow.Flow

abstract class UseCase<Param, Source> {
    abstract suspend fun execute(param: Param): Flow<Source>
    
    open suspend operator fun invoke(param: Param) = execute(param)

    abstract class ThreeParams<Param1, Param2, Source> {
        abstract suspend fun execute(param1: Param1, param2: Param2): Flow<Source>

        open suspend operator fun invoke(param1: Param1, param2: Param2) = execute(param1, param2)
    }

    abstract class NoParam<Source> : UseCase<None, Flow<Source>>() {
        abstract suspend fun execute(): Flow<Source>

        final override suspend fun execute(param: None) =
            throw UnsupportedOperationException()

        suspend operator fun invoke(): Flow<Source> = execute()
    }

    abstract class NoSource<Params> : UseCase<Params, Unit>() {
        override suspend operator fun invoke(param: Params) = execute(param)
    }

    object None
}