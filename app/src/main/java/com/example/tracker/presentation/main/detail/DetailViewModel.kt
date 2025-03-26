package com.example.tracker.presentation.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.data.database.entity.VehicleEntity
import com.example.tracker.domain.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _historyList = MutableStateFlow<List<VehicleEntity>>(emptyList())
    val historyList = _historyList.asStateFlow()

    fun getAllHistory() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllHistory()
            .catch { e ->
                e.printStackTrace()
            }
            .collect {
                _historyList.value = it
            }
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}