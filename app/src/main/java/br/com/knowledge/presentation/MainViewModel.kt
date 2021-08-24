package br.com.knowledge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.data.model.ResponseArticles
import br.com.knowledge.domain.GetActiveUserUseCase
import br.com.knowledge.domain.GetArticlesUseCase
import br.com.knowledge.domain.GetTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getActiveUserUseCase: GetActiveUserUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getArticles(token: String) {
        viewModelScope.launch {
            getArticlesUseCase(token)
                .flowOn(Dispatchers.Main)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            getTokenUseCase()
                .flowOn(Dispatchers.Default)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedToken(it.first())
                }
        }
    }

    fun getActiveUser() {
        viewModelScope.launch {
            getActiveUserUseCase()
                .flowOn(Dispatchers.Default)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.User(it)
                }
        }
    }
}

sealed class State {
    object Loading : State()
    data class Success(val articles: Response<ResponseArticles>) : State()
    data class ObtainedToken(val token: String) : State()
    data class User(val activeUser: List<ActiveUser>) : State()
    data class Error(val error: Throwable) : State()
}
