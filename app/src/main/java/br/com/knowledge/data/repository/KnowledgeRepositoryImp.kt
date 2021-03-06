package br.com.knowledge.data.repository

import android.os.RemoteException
import br.com.knowledge.data.database.AppDataBase
import br.com.knowledge.data.model.*
import br.com.knowledge.data.services.ArticlesServices
import br.com.knowledge.data.services.CreateAccountServices
import br.com.knowledge.data.services.LoginServices
import br.com.knowledge.data.services.UploadImageService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Authenticator
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response

class KnowledgeRepositoryImp(
    private val appDataBase: AppDataBase,
    private val loginServices: LoginServices,
    private val createAccountService: CreateAccountServices,
    private val articles: ArticlesServices,
    private val uploadImage: UploadImageService
) : KnowledgeRepository {

    private val dao = appDataBase.activeUserDao()

    override suspend fun login(requestLogin: RequestLogin) = flow {
        try {
            val responseLogin = loginServices.login(requestLogin)
            emit(responseLogin)
        } catch(e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }

    override suspend fun createAccount(accountData: AccountData) = flow {
        try {
            val responseCreateAccount = createAccountService.createAccount(accountData)
            emit(responseCreateAccount)
        } catch(e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw  RemoteException(errorResponse.message)
        }
    }

    override suspend fun getArticles(token: String) = flow {
        try {
            val responseArticles = articles.getArticles(token)
            emit(responseArticles)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val errorResponseArticles = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponseArticles.message)
        }
    }

    override suspend fun getMyArticles(token: String, id: Long) = flow {
        try {
            val myArticles = articles.getMyArticles(token, id)
            emit(myArticles)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val errorMyArticles = Gson().fromJson(json, ErrorResponse::class.java)
            throw  RemoteException(errorMyArticles.message)
        }
    }

    override suspend fun getToken(): Flow<List<String>> {
        return dao.getToken()
    }

    override suspend fun findAll(): Flow<List<ActiveUser>> {
        return dao.findAll()
    }

    override suspend fun save(activeUser: ActiveUser) {
        dao.save(activeUser)
    }

    override suspend fun delete(email: String) {
       dao.delete(email)
    }

    override suspend fun getEmail(): Flow<List<String>> {
        return dao.getEmail()
    }

    override suspend fun updateImageProfile(
        token: String,
        id: Long,
        file: MultipartBody.Part
    ) = flow {
        try {
            val upload = uploadImage.uploadImageProfile(token, id, file)
            emit(upload)
        } catch(e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val uploadError = Gson().fromJson(json, ErrorResponse::class.java)
            throw  RemoteException(uploadError.message)
        }
    }

    override suspend fun updateUrl(url: String, id: Long) {
        return dao.updateUrl(url, id)
    }

    override suspend fun getContentArticle(
        token: String,
        id: Long
    ): Flow<Response<ContentArticle>> = flow {
        try {
            val content = articles.getContentArticle(token, id)
            emit(content)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val contentError = Gson().fromJson(json, ErrorResponse::class.java)
            throw  RemoteException(contentError.message)
        }
    }
}