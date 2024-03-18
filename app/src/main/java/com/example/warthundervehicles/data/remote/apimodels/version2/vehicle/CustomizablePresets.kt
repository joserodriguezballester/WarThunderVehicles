package com.example.warthundervehicles.data.remote.apimodels.version2.vehicle

data class CustomizablePresets(
    val max_disbalance: Int,
    val max_load: Int,
    val max_load_left_wing: Int,
    val max_load_right_wing: Int,
    val pylons: List<Pylon>
)