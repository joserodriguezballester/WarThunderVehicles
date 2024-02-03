package com.example.warthundervehicles.data.remote.apimodels

data class Lock(
    val azimuthLimits: List<Double>,
    val elevationLimits: List<Double>,
    val period: Double,
    val pitchStabLimit: Int,
  //  val rollStabLimit: List<Int>,
    val rollStabLimit: Any,

    val type: String,
    val width: Double,
    val yawStabLimit: Double
)