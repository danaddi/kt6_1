package com.example.kt6_3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kt6_3.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }
    fun login() {
        val username = _username.value.trim()
        val password = _password.value.trim()

        if (username.isEmpty() || password.isEmpty()) {
            _state.value = LoginState.Error("Заполните все поля")
            return
        }

        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase(username, password)
            result.fold(
                onSuccess = { response ->
                    val token = response.token ?: throw Exception("Токен не получен")
                    _state.value = LoginState.Success(token)
                },
                onFailure = { exception ->
                    val errorMessage = when {
                        exception.message?.contains("Unable to resolve host") == true ->
                            "Нет соединения с интернетом"
                        exception.message?.contains("401") == true ->
                            "Неверные учетные данные"
                        else -> exception.message ?: "Произошла ошибка"
                    }
                    _state.value = LoginState.Error(errorMessage)
                }
            )
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }

    class Factory(
        private val loginUseCase: LoginUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(loginUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}