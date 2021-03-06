package br.com.knowledge.data.repository

import br.com.knowledge.data.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
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

    suspend fun updateImageProfile(token: String, id: Long, file: MultipartBody.Part) : Flow<Response<ResponseUploadImage>>

    suspend fun updateUrl(url: String, id: Long)

    suspend fun getContentArticle(token: String, id: Long) : Flow<Response<ContentArticle>>
}