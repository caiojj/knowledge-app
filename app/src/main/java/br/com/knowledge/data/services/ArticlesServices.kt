package br.com.knowledge.data.services

import br.com.knowledge.data.model.ContentArticle
import br.com.knowledge.data.model.ResponseArticles
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ArticlesServices {

    @GET("/articles")
    suspend fun getArticles(@Header("Authorization") token: String) : Response<ResponseArticles>

    @GET("/myArticles/{id}")
    suspend fun getMyArticles(
        @Header("Authorization") token: String,
        @Path("id") id: Long) : Response<ResponseArticles>

    @GET("/articles/{id}")
    suspend fun getContentArticle(
        @Header("Authorization") token: String,
        @Path("id") id: Long) : Response<ContentArticle>
}