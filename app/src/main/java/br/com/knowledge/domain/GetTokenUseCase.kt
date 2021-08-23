package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow

class GetTokenUseCase(
    private val repository: KnowledgeRepository
) : UseCase.NoParam<List<String>>() {
    override suspend fun execute(): Flow<List<String>> {
        return repository.getToken()
    }
}