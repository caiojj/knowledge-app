package br.com.knowledge.data.repository

import android.os.RemoteException
import android.util.Log
import br.com.knowledge.data.module.ErrorResponse
import br.com.knowledge.data.module.Login
import br.com.knowledge.data.services.LoginServices
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class KnowledgeRepositoryImp(
    private val service: LoginServices
) : KnowledgeRepository {

    override suspend fun login(login: Login) = flow {
        try {
            val responseLogin = service.login(login)
            emit(responseLogin)
        } catch(e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }
}