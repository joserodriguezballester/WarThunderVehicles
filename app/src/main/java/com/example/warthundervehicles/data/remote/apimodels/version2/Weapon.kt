package com.example.warthundervehicles.data.remote.apimodels.version2

data class Weapon(
    val ammos: List<Ammo>,
    val count: Int,
    val name: String,
    val weapon_type: String
)