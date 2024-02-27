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

fun textoSinDecimales(statValue: Double): String {
    val textoSinDecimales = if (statValue % 1 == 0.0) {
        statValue.toInt().toString() // Si es un número entero, lo convierte a Int y luego a String
    } else {
        statValue.toString() // De lo contrario, simplemente convierte el Double a String
    }
    return textoSinDecimales
}

// Función para obtener el texto correspondiente a una propiedad, con manejo de caso desconocido
fun getTextForProperty(property: String): String {
    return Constants.LIST_AERODYNAMICS_TEXTS.getOrDefault(property, Constants.defaultText)
}

// Función para obtener la unidad de medida correspondiente a una propiedad, con manejo de caso desconocido
fun getUnitForProperty(property: String): String {
    return Constants.LIST_AERODYNAMICS_UNITS.getOrDefault(property, Constants.defaultUnit)
}