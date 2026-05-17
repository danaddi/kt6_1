package com.example.kt6_2.presentation.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt6_2.domain.model.NobelLaureate
import com.example.kt6_2.domain.repository.NobelPrizeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LaureateDetailState {
    object Loading : LaureateDetailState()
    data class Success(val laureate: NobelLaureate) : LaureateDetailState()
    data class Error(val message: String) : LaureateDetailState()
}

class NobelPrizeDetailViewModel(
    private val repository: NobelPrizeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<LaureateDetailState>(LaureateDetailState.Loading)
    val state: StateFlow<LaureateDetailState> = _state.asStateFlow()

    fun loadLaureateDetail(laureateId: String) {
        viewModelScope.launch {
            _state.value = LaureateDetailState.Loading

            repository.getLaureateById(laureateId).fold(
                onSuccess = { laureate ->
                    _state.value = LaureateDetailState.Success(laureate)
                },
                onFailure = { error ->
                    _state.value = LaureateDetailState.Error(
                        error.message ?: "Ошибка загрузки данных"
                    )
                }
            )
        }
    }
}