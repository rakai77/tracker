package com.example.tracker.data.mapper

import com.example.tracker.data.database.entity.VehicleEntity
import com.example.tracker.domain.model.Vehicle

fun VehicleEntity.toDomain(): Vehicle {
    return Vehicle(
        lat = this.lat,
        lng = this.lng,
        speed = this.speed,
        engineStatus = this.engineStatus,
        doorStatus = this.doorStatus,
        timestamp = this.timestamp
    )
}

fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        lat = this.lat,
        lng = this.lng,
        speed = this.speed,
        engineStatus = this.engineStatus,
        doorStatus = this.doorStatus,
        timestamp = this.timestamp
    )
}