package com.example.tracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tracker.data.database.entity.VehicleDao
import com.example.tracker.data.database.entity.VehicleEntity

@Database(entities = [VehicleEntity::class], version = 1)
abstract class VehicleDatabase : RoomDatabase() {
    abstract fun vehicleHistoryDao(): VehicleDao

    companion object {
        @Volatile
        private var INSTANCE: VehicleDatabase? = null

        fun getDatabase(context: Context): VehicleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDatabase::class.java,
                    "tracker.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}