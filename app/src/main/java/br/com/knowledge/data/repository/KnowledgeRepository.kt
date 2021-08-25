package br.com.knowledge.data.repository

import br.com.knowledge.data.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.Authenticator
import org.jetbrains.annotations.NotNull
import retrofit2.Response


interface KnowledgeRepository {

    suspend fun login(requestLogin: RequestLogin): Flow<Response<ResponseLogin>>

    suspend fun createAccount(accountData: AccountData): Flow<Response<Void>>

    suspend fun getArticles(token: String) : Flow<Response<ResponseArticles>>

    suspend fun getMyArticles(token: String, id: Long) : Flow<Response<ResponseArticles>>

    suspend fun getToken() : Flow<List<String>>

    suspend fun findAll(): Flow<List<ActiveUser>>

    suspend fun save(activeUser: ActiveUser)

    suspend fun delete(email: String)

    suspend fun getEmail() : Flow<List<String>>
}