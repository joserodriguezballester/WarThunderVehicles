package com.example.warthundervehicles.modelsApp


data class MachineListItem(
    val name: String,
    val identifier: String,
    val imageUrl: String,
    val bandera: Int,
    val country: String,
    val era: Int,
    val type: String,
    val arcade_br:Double,
)
// name = transformName(identifier, PREFIXES),
// bandera = transformCountry(country),
//