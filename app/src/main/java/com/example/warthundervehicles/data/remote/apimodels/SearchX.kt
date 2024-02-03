package com.example.warthundervehicles.data.remote.apimodels

data class SearchX(
    val band: Int,
    val prf: Int,
    val pulsePower: Int,
    val pulseWidth: Double,
    val range: Int,
    val rangeMax: Int,
    val rcs: Double
)