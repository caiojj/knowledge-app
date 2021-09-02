package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.ContentArticle
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetContentArticleUseCase(
    private val repository: KnowledgeRepository
) : UseCase.ThreeParams<String, Long, Response<ContentArticle>>() {
    override suspend fun execute(param1: String, param2: Long): Flow<Response<ContentArticle>> {
        return repository.getContentArticle(param1, param2)
    }
}