package com.example.kt6_3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kt6_3.domain.model.User
import com.example.kt6_3.domain.repository.AuthRepository
import com.example.kt6_3.domain.usecase.GetUserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UserDetailState {
    object Loading : UserDetailState()
    data class Success(val user: User) : UserDetailState()
    data class Error(val message: String) : UserDetailState()
}

class UserDetailViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val state: StateFlow<UserDetailState> = _state.asStateFlow()

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut: StateFlow<Boolean> = _loggedOut.asStateFlow()

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            _state.value = UserDetailState.Loading
            val result = getUserDetailUseCase(userId)
            result.fold(
                onSuccess = { user ->
                    _state.value = UserDetailState.Success(user)
                },
                onFailure = { exception ->
                    _state.value = UserDetailState.Error(
                        exception.message ?: "Ошибка загрузки"
                    )
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _loggedOut.value = true
        }
    }

    class Factory(
        private val getUserDetailUseCase: GetUserDetailUseCase,
        private val authRepository: AuthRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserDetailViewModel(getUserDetailUseCase, authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}