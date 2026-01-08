package ru.otus.basicarchitecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddressViewModel : ViewModel() {

    val networkService = NetworkApi.retrofitService

    private val _hints = MutableStateFlow<List<AddressSuggestion>>(emptyList())
    val hints: StateFlow<List<AddressSuggestion>> = _hints

    fun getHints(input: String) {
        viewModelScope.launch {
            try {
                val query = AddressQuery(input)
                val response: AddressResponse = networkService.getHints(query)

                _hints.value = response.suggestions
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}