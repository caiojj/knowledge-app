package br.com.knowledge.presentation

import androidx.lifecycle.*
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.data.model.ResponseArticles
import br.com.knowledge.domain.*
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
    private val getActiveUserUseCase: GetActiveUserUseCase,
    private val deleteActiveUserUseCase: DeleteActiveUserUseCase,
    private val getEmailUseCase: GetEmailUseCase,
    private val getMyArticlesUseCase: GetMyArticlesUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
    get() = _state

    fun getArticles(token: String) {
        viewModelScope.launch {
            getArticlesUseCase(token)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.LoadingAllArticles
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedArticles(it)
                }
        }
    }

    fun getMyArticles(token: String, id: Long) {
        viewModelScope.launch {
            getMyArticlesUseCase(token, id)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.LoadingMyArticles
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedMyArticles(it)
                }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            getTokenUseCase()
                .flowOn(Dispatchers.Default)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedToken(it.firstOrNull())
                }
        }
    }

    fun getActiveUser() {
        viewModelScope.launch {
            getActiveUserUseCase()
                .flowOn(Dispatchers.Default)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedUser(it)
                    _state.postValue(State.ObtainedUser(it))
                }
        }
    }

    fun getEmail() {
        viewModelScope.launch {
            getEmailUseCase()
                .flowOn(Dispatchers.Main)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedEmail(it.firstOrNull())
                }
        }
    }

    fun deleteActivityUser(email: String) {
        viewModelScope.launch {
            deleteActiveUserUseCase(email)
                .flowOn(Dispatchers.Default)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Deleted
                }
        }
    }
}

sealed class State {
    object LoadingAllArticles : State()
    object LoadingMyArticles: State()
    object Deleted : State()
    data class ObtainedArticles(val articles: Response<ResponseArticles>) : State()
    data class ObtainedMyArticles(val articles: Response<ResponseArticles>) : State()
    data class ObtainedToken(val token: String?) : State()
    data class ObtainedEmail(val email: String?) : State()
    data class ObtainedUser(val activeUser: List<ActiveUser>) : State()
    data class Error(val error: Throwable) : State()
}
