package br.com.knowledge.data.repository

import br.com.knowledge.data.module.Login
import br.com.knowledge.data.module.ResponseLogin
import kotlinx.coroutines.flow.Flow


interface KnowledgeRepository {

    suspend fun login(login: Login): Flow<ResponseLogin>

}