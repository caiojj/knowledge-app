package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveActiveUserUseCase(
    private val repository: KnowledgeRepository
): UseCase.NoSource<ActiveUser>() {
    override suspend fun execute(param: ActiveUser): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }
}