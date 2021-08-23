package br.com.knowledge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.knowledge.domain.GetTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getToken() {
        viewModelScope.launch {
            getTokenUseCase()
                .flowOn(Dispatchers.Default)
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.ObtainedToken(it)
                }
        }
    }

    sealed class State {
        data class ObtainedToken(val token: List<String>) : State()
        data class Error(val error: Throwable) : State()
    }
}