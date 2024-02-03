package com.example.warthundervehicles.utils

import com.example.warthundervehicles.R

fun Any?.customToString(): String {
    return when (this) {
        is String -> this
        is Array<*> -> this.joinToString(", ")
        is List<*> -> this.joinToString(",")
        else -> "Tipo no válido"
    }
}


fun transformSiluetas(type: String): Int {
    val siluetas = mapOf(
        "tank" to R.drawable.ic_silueta_tank,
        "plane" to R.drawable.ic_silueta_plane,
        "ship" to R.drawable.ic_silueta_ship,

    )
    return siluetas[type] ?: 0
}