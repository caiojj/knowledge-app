package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteActiveUserUseCase(
    private val repository: KnowledgeRepository
) : UseCase.NoSource<String>(){
    override suspend fun execute(param: String): Flow<Unit> {
        return flow {
            repository.delete(param)
            emit(Unit)
        }
    }
}