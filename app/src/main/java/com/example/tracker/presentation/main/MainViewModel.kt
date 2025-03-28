package com.example.tracker.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.data.database.entity.VehicleEntity
import com.example.tracker.domain.repository.VehicleRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _vehicleLocation = MutableStateFlow(LatLng(-6.200000, 106.816666))
    val vehicleLocation = _vehicleLocation.asStateFlow()

    private val _vehicleSpeed = MutableStateFlow(0)
    val vehicleSpeed = _vehicleSpeed.asStateFlow()

    private val _engineStatus = MutableStateFlow(true)
    val engineStatus = _engineStatus.asStateFlow()

    private val _doorStatus = MutableStateFlow(false)
    val doorStatus = _doorStatus.asStateFlow()

    private val _isSimulating = MutableStateFlow(false)
    val isSimulating = _isSimulating.asStateFlow()

    private val path = listOf(
        LatLng(-6.200000, 106.816666),
        LatLng(-6.201000, 106.817000),
        LatLng(-6.202000, 106.818000),
        LatLng(-6.203000, 106.819000)
    )
    private var pathIndex = 0

    fun toggleSimulation() {
        if (_isSimulating.value) {
            stopSimulation()
        } else {
            startSimulation()
        }
    }

    private fun startSimulation() {
        _isSimulating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            while (_isSimulating.value) {
                val currentLocation = path[pathIndex % path.size]
                _vehicleLocation.value = currentLocation
                _vehicleSpeed.value = (40..120).random()
                _engineStatus.value = (0..1).random() == 1
                _doorStatus.value = (0..1).random() == 1
                pathIndex++

                saveVehicleHistory(
                    currentLocation,
                    _vehicleSpeed.value,
                    _engineStatus.value,
                    _doorStatus.value
                )
                delay(3000)
            }
        }
    }

    private fun stopSimulation() {
        _isSimulating.value = false
    }

    private fun saveVehicleHistory(
        location: LatLng,
        speed: Int,
        engineStatus: Boolean,
        doorStatus: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        val history = VehicleEntity(
            lat = location.latitude,
            lng = location.longitude,
            speed = speed,
            engineStatus = engineStatus,
            doorStatus = doorStatus,
            timestamp = System.currentTimeMillis()
        )
        repository.insertHistory(history)
    }
}