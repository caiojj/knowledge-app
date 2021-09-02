package br.com.knowledge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.data.model.ContentArticle
import br.com.knowledge.domain.GetContentArticleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response

class ReadArticleViewModel(
    private val getContentArticleUseCase: GetContentArticleUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
    get() = _state

    fun getContentArticle(token: String, id: Long) {
        viewModelScope.launch {
            getContentArticleUseCase(token, id)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedContent(it)
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class ObtainedContent(val content: Response<ContentArticle>) : State()
        data class Error(val error: Throwable) : State()
    }
}
