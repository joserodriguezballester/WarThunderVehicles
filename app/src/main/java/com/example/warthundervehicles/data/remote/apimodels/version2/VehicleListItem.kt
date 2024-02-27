package com.example.warthundervehicles.data.remote.apimodels.version2

data class VehicleListItem(
    val arcade_br: Int,
    val country: String,
    val era: Int,
    val event: String,
    val ge_cost: Int,
    val identifier: String,
    val images: Images,
    val is_gift: Boolean,
    val is_premium: Boolean,
    val realistic_br: Int,
    val release_date: String,
    val req_exp: Int,
    val simulator_br: Int,
    val value: Int,
    val vehicle_type: String
)