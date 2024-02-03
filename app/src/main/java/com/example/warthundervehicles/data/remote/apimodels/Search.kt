package com.example.warthundervehicles.data.remote.apimodels

data class Search(
    val azimuthLimits: List<Double>,
    val barHeight: Double,
    val barsCount: Int,
    val elevationLimits: List<Double>,
    val period: Double,
    val pitchStabLimit: Int,
    val rollStabLimit: Int,
    val type: String,
    val yawStabLimit: Int
)