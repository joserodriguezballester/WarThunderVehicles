package com.example.warthundervehicles.data.remote.apimodels

data class LosLock(
    val azimuthLimits: List<Int>,
    val elevationLimits: List<Int>,
    val period: Double,
    val pitchStabLimit: Int,
    val rollStabLimit: Any,
 //   val rollStabLimit: List<Int>,
    val type: String,
    val width: Double,
    val yawStabLimit: Int
)