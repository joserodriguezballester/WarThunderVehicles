package com.example.warthundervehicles.data.remote.apimodels.version2

data class Modification(
    val ge_cost: Int,
    val name: String,
    val repair_coeff: Double,
    val req_exp: Int,
    val required_modification: String,
    val tier: Int,
    val value: Int
)