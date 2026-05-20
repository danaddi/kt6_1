package com.example.kt6_3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kt6_3.domain.model.User
import com.example.kt6_3.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UsersListState {
    object Loading : UsersListState()
    data class Success(val users: List<User>) : UsersListState()
    data class Error(val message: String) : UsersListState()
}

class UsersListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UsersListState>(UsersListState.Loading)
    val state: StateFlow<UsersListState> = _state.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _state.value = UsersListState.Loading
            val result = getUsersUseCase()
            result.fold(
                onSuccess = { users ->
                    _state.value = UsersListState.Success(users)
                },
                onFailure = { exception ->
                    _state.value = UsersListState.Error(
                        exception.message ?: "Ошибка загрузки"
                    )
                }
            )
        }
    }

    class Factory(
        private val getUsersUseCase: GetUsersUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersListViewModel(getUsersUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}