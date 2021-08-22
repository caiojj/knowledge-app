package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.ResponseArticles
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetArticlesUseCase(
    private val repository: KnowledgeRepository
) : UseCase<String, Response<ResponseArticles>>() {
    override suspend fun execute(param: String): Flow<Response<ResponseArticles>> {
        return repository.getArticles(param)
    }
}