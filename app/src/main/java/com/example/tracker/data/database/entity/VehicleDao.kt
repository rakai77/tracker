package com.example.tracker.data.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertHistory(history: VehicleEntity)

    @Query("SELECT * FROM vehicle_table ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<VehicleEntity>>

    @Query("DELETE FROM vehicle_table")
    suspend fun deleteAll()
}