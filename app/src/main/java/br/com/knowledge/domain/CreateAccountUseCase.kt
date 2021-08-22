package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.AccountData
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CreateAccountUseCase(
    private val repository: KnowledgeRepository
) : UseCase<AccountData, Response<Void>>() {

    override suspend fun execute(param: AccountData): Flow<Response<Void>> {
        return repository.createAccount(param)
    }
}