package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.RequestLogin
import br.com.knowledge.data.model.ResponseLogin
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ResponseLoginUseCase(
    private val repository: KnowledgeRepository
) : UseCase<RequestLogin, Response<ResponseLogin>>() {

    override suspend fun execute(param: RequestLogin): Flow<Response<ResponseLogin>> {
        return repository.login(param)
    }
}