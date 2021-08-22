package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.module.ActiveUser
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow

class GetActiveUserUseCase(
    private val repository: KnowledgeRepository
) : UseCase.NoParam<List<ActiveUser>>() {
    override suspend fun execute(): Flow<List<ActiveUser>> {
        return repository.findAll()
    }
}