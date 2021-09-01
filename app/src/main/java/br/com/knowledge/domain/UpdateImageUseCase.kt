package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateImageUseCase(
    private val repository: KnowledgeRepository
) : UseCase.TwoParamsNoSource<String, Long>() {
    override suspend fun execute(param1: String, param2: Long): Flow<Unit> {
        return flow {
            repository.updateUrl(param1, param2)
            emit(Unit)
        }
    }
}