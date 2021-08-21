package br.com.knowledge.data.services

import br.com.knowledge.data.module.AccountData
import br.com.knowledge.data.module.Login
import br.com.knowledge.data.module.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {

    @POST("/singing")
    suspend fun login(@Body login: Login) : ResponseLogin

    @POST("/signup")
    suspend fun createAccount(@Body dataAccount: AccountData) : Response<Void>
}