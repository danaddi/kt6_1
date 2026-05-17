package com.example.kt6_2.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt6_2.domain.model.NobelPrize
import com.example.kt6_2.domain.repository.NobelPrizeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FilterState(
    val year: String = "",
    val category: String = ""
)

sealed class NobelPrizeListState {
    object Loading : NobelPrizeListState()
    data class Success(val prizes: List<NobelPrize>) : NobelPrizeListState()
    data class Error(val message: String) : NobelPrizeListState()
}

class NobelPrizeListViewModel(
    private val repository: NobelPrizeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<NobelPrizeListState>(NobelPrizeListState.Loading)
    val state: StateFlow<NobelPrizeListState> = _state.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    init {
        loadPrizes()
    }

    fun loadPrizes() {
        viewModelScope.launch {
            _state.value = NobelPrizeListState.Loading

            val year = _filterState.value.year.ifEmpty { null }
            val category = _filterState.value.category.ifEmpty { null }

            repository.getNobelPrizes(
                year = year,
                category = category,
                limit = 100
            ).fold(
                onSuccess = { prizes ->
                    // Фильтруем пустые лауреаты и преобразуем в плоский список
                    val allLaureates = prizes.flatMap { prize ->
                        prize.laureates.map { laureate ->
                            prize to laureate
                        }
                    }

                    val filteredPrizes = allLaureates.map { (prize, laureate) ->
                        prize.copy(laureates = listOf(laureate))
                    }

                    _state.value = NobelPrizeListState.Success(filteredPrizes)
                },
                onFailure = { error ->
                    _state.value = NobelPrizeListState.Error(
                        error.message ?: "Неизвестная ошибка"
                    )
                }
            )
        }
    }

    fun updateFilter(year: String, category: String) {
        _filterState.value = FilterState(year = year, category = category)
        loadPrizes()
    }
}