package br.com.knowledge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.data.model.RequestLogin
import br.com.knowledge.data.model.ResponseLogin
import br.com.knowledge.domain.ResponseLoginUseCase
import br.com.knowledge.domain.SaveActiveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val saveActiveUserUseCase: SaveActiveUserUseCase,
    private val responseLoginUseCase: ResponseLoginUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun executeLogin(requestLogin: RequestLogin) {
        viewModelScope.launch {
            responseLoginUseCase(requestLogin)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it.message)
                }
                .collect {
                    _state.value = State.Logged(it)
                }
        }
    }

    fun insert(activeUser: ActiveUser) {
        viewModelScope.launch {
            saveActiveUserUseCase(activeUser)
                .flowOn(Dispatchers.Default)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it.message)
                }
                .collect {
                    _state.value = State.Saved
                }
        }
    }

    sealed class State {
        object Loading: State()
        object Saved: State()
        data class Logged(val body: Response<ResponseLogin>): State()
        data class Error(val error: String?): State()
    }
}