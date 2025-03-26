package com.example.tracker.domain.repository

import com.example.tracker.data.database.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun insertHistory(vehicleEntity: VehicleEntity)
    fun getAllHistory(): Flow<List<VehicleEntity>>
    suspend fun deleteAll()
}