package br.com.knowledge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.data.model.ResponseArticles
import br.com.knowledge.domain.GetArticlesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response

class ArticlesViewModel(
private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state


    fun getArticles(token: String) {
        viewModelScope.launch {
            getArticlesUseCase(token)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }
}

sealed class State {
    object Loading : State()
    data class Success(val articles: Response<ResponseArticles>) : State()
    data class Error(val error: Throwable) : State()
}