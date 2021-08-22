package br.com.knowledge.data.services

import br.com.knowledge.data.model.AccountData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateAccountServices {

    @POST("/signup")
    suspend fun createAccount(@Body dataAccount: AccountData) : Response<Void>

}