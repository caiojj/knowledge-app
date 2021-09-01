package br.com.knowledge.domain

import br.com.knowledge.core.UseCase
import br.com.knowledge.data.model.ResponseUploadImage
import br.com.knowledge.data.repository.KnowledgeRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class UploadImageUseCase(
    private val repository: KnowledgeRepository
) : UseCase.FourParams<String, Long, MultipartBody.Part, Response<ResponseUploadImage>>() {
    override suspend fun execute(
        param1: String,
        param2: Long,
        param3: MultipartBody.Part
    ): Flow<Response<ResponseUploadImage>> {
        return repository.updateImageProfile(param1, param2, param3)
    }
}