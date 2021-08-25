package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.ResponseArticles
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetMyArticlesUseCase(
    private val repository: KnowledgeRepository
) : UseCase.ThreeParams<String, Long, Response<ResponseArticles>>() {
    override suspend fun execute(param1: String, param2: Long): Flow<Response<ResponseArticles>> {
        return repository.getMyArticles(param1, param2)
    }
}