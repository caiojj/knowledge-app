package br.com.knowledge.data.services

import br.com.knowledge.data.model.ResponseArticles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ArticlesServices {

    @GET("/articles")
    suspend fun getArticles(@Header("Authorization") token: String) : Response<ResponseArticles>

}