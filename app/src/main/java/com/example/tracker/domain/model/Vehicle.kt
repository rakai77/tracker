package com.example.tracker.domain.model

data class Vehicle(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val speed: Int = 0,
    val engineStatus: Boolean = false,
    val doorStatus: Boolean = false,
    val timestamp: Long = 0L
)
