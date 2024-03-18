package com.example.warthundervehicles.data.remote.apimodels.version2.vehicle

data class Ammo(
    val caliber: Double,
    val explosive_mass: Double,
    val explosive_type: String,
    val mass: Double,
    val max_distance: Int,
    val name: String,
    val speed: Int,
    val type: String
)