package br.com.knowledge.core

import kotlinx.coroutines.flow.Flow

abstract class UseCase<Param, Source> {

    abstract suspend fun execute(param: Param): Flow<Source>
    
    open suspend operator fun invoke(param: Param) = execute(param)

    abstract class NoSource<Params> : UseCase<Params, Unit>() {
        override suspend operator fun invoke(param: Params) = execute(param)
    }

    abstract class TwoParamsNoSource<Param1, Param2> {
        abstract suspend fun execute(param1: Param1, param2: Param2): Flow<Unit>

        open suspend operator fun invoke(param1: Param1, param2: Param2) = execute(param1, param2)
    }

    abstract class NoParam<Source> : UseCase<None, Flow<Source>>() {
        abstract suspend fun execute(): Flow<Source>

        final override suspend fun execute(param: None) =
            throw UnsupportedOperationException()

        suspend operator fun invoke(): Flow<Source> = execute()
    }

    abstract class ThreeParams<Param1, Param2, Source> {
        abstract suspend fun execute(param1: Param1, param2: Param2): Flow<Source>

        open suspend operator fun invoke(param1: Param1, param2: Param2) = execute(param1, param2)
    }

    abstract class FourParams<Param1, Param2, Param3,Source> {
        abstract suspend fun execute(param1: Param1, param2: Param2, param3: Param3): Flow<Source>

        open suspend operator fun invoke(param1: Param1, param2: Param2, param3: Param3) = execute(param1, param2, param3)
    }

    object None
}