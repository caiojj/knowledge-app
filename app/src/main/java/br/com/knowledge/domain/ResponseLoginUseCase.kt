package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.module.Login
import br.com.knowledge.data.module.ResponseLogin
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow

class ResponseLoginUseCase(
    private val repository: KnowledgeRepository
) : UseCase<Login, ResponseLogin>() {

    override suspend fun execute(param: Login): Flow<ResponseLogin> {
        return repository.login(param)
    }
}