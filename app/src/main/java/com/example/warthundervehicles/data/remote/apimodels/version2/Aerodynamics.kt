package com.example.warthundervehicles.data.remote.apimodels.version2

data class Aerodynamics(
    val empty_weight: Double,
    val length: Double,
    val max_altitude: Int,
    val max_speed_at_altitude: Int,
    val max_takeoff_weight: Int,
    val runway_length_required: Double,
    val turn_time: Double,
    val wing_area: Double,
    val wingspan: Double
)