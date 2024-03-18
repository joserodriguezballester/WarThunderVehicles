package com.example.warthundervehicles.data.remote.apimodels.version2

data class WeaponX(
    val ammos: List<AmmoX>,
    val count: Int,
    val name: String,
    val weapon_type: String
)