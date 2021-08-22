package br.com.knowledge.data.repository

import br.com.knowledge.data.module.AccountData
import br.com.knowledge.data.module.ActiveUser
import br.com.knowledge.data.module.Login
import br.com.knowledge.data.module.ResponseLogin
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface KnowledgeRepository {

    suspend fun login(login: Login): Flow<ResponseLogin>

    suspend fun createAccount(accountData: AccountData): Flow<Response<Void>>

    suspend fun findAll(): Flow<List<ActiveUser>>

    suspend fun save(activeUser: ActiveUser)

    suspend fun delete(email: String)

}