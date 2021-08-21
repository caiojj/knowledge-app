package br.com.knowledge.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.data.module.Login
import br.com.knowledge.data.module.ResponseLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val responseLoginUseCase: ResponseLoginUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun executeLogin(login: Login) {
        viewModelScope.launch {
            responseLoginUseCase(login)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Logged(it)
                }
        }
    }

    sealed class State {
        object Loading: State()
        data class Logged(val body: ResponseLogin): State()
        data class Error(val error: Throwable) : State()
    }
}