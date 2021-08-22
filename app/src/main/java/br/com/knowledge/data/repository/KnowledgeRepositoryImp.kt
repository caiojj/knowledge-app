package br.com.knowledge.data.repository

import android.os.RemoteException
import br.com.knowledge.data.database.AppDataBase
import br.com.knowledge.data.module.AccountData
import br.com.knowledge.data.module.ActiveUser
import br.com.knowledge.data.module.ErrorResponse
import br.com.knowledge.data.module.Login
import br.com.knowledge.data.services.LoginServices
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class KnowledgeRepositoryImp(
    private val appDataBase: AppDataBase,
    private val service: LoginServices
) : KnowledgeRepository {

    private val dao = appDataBase.activeUserDao()

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

    override suspend fun createAccount(accountData: AccountData) = flow {
        try {
            val responseCreateAccount = service.createAccount(accountData)
            emit(responseCreateAccount)
        } catch(e: HttpException) {
            val json = e.response()?.errorBody()?.toString()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw  RemoteException(errorResponse.message)
        }
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
}