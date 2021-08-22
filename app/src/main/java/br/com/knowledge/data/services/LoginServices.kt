package br.com.knowledge.data.services

import br.com.knowledge.data.model.RequestLogin
import br.com.knowledge.data.model.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {

    @POST("/singing")
    suspend fun login(@Body requestLogin: RequestLogin) : Response<ResponseLogin>

}