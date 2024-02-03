package com.example.warthundervehicles.data.remote.apimodels

data class Track(
    val azimuthLimits: List<Double>,
    val elevationLimits: List<Double>,
    val pitchStabLimit: Int,
 //   val rollStabLimit: List<Int>,
    val rollStabLimit: Any,

    val type: String,
    val yawStabLimit: Int
)