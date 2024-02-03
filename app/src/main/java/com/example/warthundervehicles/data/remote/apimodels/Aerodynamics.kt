package com.example.warthundervehicles.data.remote.apimodels

data class Aerodynamics(
    val emptyWeight: Double,
    val lenght: Double,
    val maxAltitude: Double,
    val maxSpeedAtAlt: Double,
    val maxTakeoffWeight: Double,
    val runwayLenRequired: Double,
    val turnTime: Double,
    val wingArea: Double,
    val wingspan: Double
)