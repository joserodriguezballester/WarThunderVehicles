package com.example.warthundervehicles.data.remote.apimodels

data class Ammo(
    val caliber: Double,
    val explosiveMass: Double,
    val explosiveType: String,
    val mass: Double,
    val maxDistance: Double,
    val name: String,
    val speed: Double,
    val type: Any
)