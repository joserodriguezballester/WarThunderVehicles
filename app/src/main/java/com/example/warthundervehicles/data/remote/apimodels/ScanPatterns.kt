package com.example.warthundervehicles.data.remote.apimodels

data class ScanPatterns(
    val lock: Lock,
    val losLock: LosLock,
    val search: Search,
    val track: Track
)