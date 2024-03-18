package com.example.warthundervehicles.data.remote.apimodels.version2.vehicle

data class SelectableWeapon(
    val ammos: List<Ammo>,
    val count: Int,
    val name: String,
    val weapon_type: String
)