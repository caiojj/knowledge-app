package br.com.knowledge.data.services

import br.com.knowledge.data.model.ResponseUploadImage
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface UploadImageService {

    @Multipart
    @POST("/profile/{id}")
    suspend fun uploadImageProfile(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Part file: MultipartBody.Part
    ) : Response<ResponseUploadImage>
}