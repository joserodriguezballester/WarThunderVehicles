package com.example.warthundervehicles.data.remote.apimodels

data class WeaponX(
    val ammos: List<Ammo>,
    val count: Int,
    val name: String,
   // val type: String
    val type: Any
)