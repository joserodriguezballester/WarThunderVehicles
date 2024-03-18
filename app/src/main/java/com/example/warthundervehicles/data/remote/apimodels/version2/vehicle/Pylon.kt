package com.example.warthundervehicles.data.remote.apimodels.version2.vehicle

data class Pylon(
    val index: Int,
    val selectable_weapons: List<SelectableWeapon>,
    val used_for_disbalance: Boolean
)