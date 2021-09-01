package br.com.knowledge.presentation

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.data.model.ResponseUploadImage
import br.com.knowledge.domain.UpdateImageUseCase
import br.com.knowledge.domain.UploadImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class EditProfileViewModel(
    private val uploadImageUseCase: UploadImageUseCase,
    private val updateImageUseCase: UpdateImageUseCase,
    private val context: Context
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
    get() = _state

    fun updateImage(url: String, id: Long) {
        viewModelScope.launch {
            updateImageUseCase(url, id)
                .flowOn(Dispatchers.Default)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.SavedUrl
                }
        }
    }

    fun uploadImage(token: String, id: Long, file: Intent) {
        val requestBody = convertFromMultipartBody(file)

        viewModelScope.launch {
            uploadImageUseCase(token, id, requestBody)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Uploaded(it)
                }
        }
    }

    private fun getPathImage(file: Intent) : String? {
        var field: Array<String>? = null
        val cursor = context.contentResolver.query(file.data!!, field, null, null, null, null)
        cursor?.moveToFirst()
        val path = cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
        cursor?.close()
        return path
    }

    private fun convertFromMultipartBody(file: Intent): MultipartBody.Part {
        val imageFile = File(getPathImage(file))
        val create = imageFile.asRequestBody(file.type?.toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("file", imageFile.name, create)
    }

    sealed class State {
        object Loading: State()
        object SavedUrl: State()
        data class Uploaded(val res: Response<ResponseUploadImage>): State()
        data class Error(val error: Throwable) : State()
    }
}