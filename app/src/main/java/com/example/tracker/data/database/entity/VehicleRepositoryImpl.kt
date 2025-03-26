package com.example.tracker.data.database.entity

import com.example.tracker.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow

class VehicleRepositoryImpl(
    private val vehicleDao: VehicleDao
) : VehicleRepository {
    override suspend fun insertHistory(vehicleEntity: VehicleEntity) {
        vehicleDao.insertHistory(vehicleEntity)
    }

    override fun getAllHistory(): Flow<List<VehicleEntity>> {
        return vehicleDao.getAllHistory()
    }

    override suspend fun deleteAll() {
        return vehicleDao.deleteAll()
    }
}