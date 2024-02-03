package com.example.warthundervehicles.data.remote.apimodels

data class Sensors(
    val IRST: Boolean,
    val MTI: Boolean,
    val PD: Boolean,
    val TWS: Boolean,
    val name: String,
    val scanPatterns: ScanPatterns,
    val separateAntenna: String,
    val transivers: Transivers,
    val type: String
)