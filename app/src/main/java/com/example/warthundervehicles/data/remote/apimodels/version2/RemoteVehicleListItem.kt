package com.example.warthundervehicles.data.remote.apimodels.version2

data class RemoteVehicleListItem(
    val aerodynamics: Aerodynamics,
    val arcade_br: Double,
    val country: String,
    val era: Int,
    val event: String,
    val ge_cost: Int,
    val identifier: String,
    val images: Images,
    val is_gift: Boolean,
    val is_premium: Boolean,
    val realistic_br: Double,
    val release_date: String,
    val req_exp: Int,
    val simulator_br: Double,
    val value: Int,
    val vehicle_type: String
)