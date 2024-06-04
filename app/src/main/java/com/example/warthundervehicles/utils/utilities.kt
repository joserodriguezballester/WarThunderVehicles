package com.example.warthundervehicles.utils

import android.util.Log
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.warthundervehicles.R
import com.example.warthundervehicles.ui.theme.Tier1
import com.example.warthundervehicles.ui.theme.Tier2
import com.example.warthundervehicles.ui.theme.Tier3
import com.example.warthundervehicles.ui.theme.Tier4
import com.example.warthundervehicles.ui.theme.Tier5
import com.example.warthundervehicles.ui.theme.Tier6
import com.example.warthundervehicles.ui.theme.Tier7
import com.example.warthundervehicles.ui.theme.Tier8
import com.example.warthundervehicles.ui.theme.bomber
import com.example.warthundervehicles.ui.theme.divebomber
import com.example.warthundervehicles.ui.theme.figther
import com.example.warthundervehicles.ui.theme.stormovik
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_AIR
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_NAVAL
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_TANK
import java.util.Locale

fun Any?.customToString(): String {
    return when (this) {
        is String -> this
        is Array<*> -> this.joinToString(", ")
        is List<*> -> this.joinToString(",")
        else -> "Tipo no válido"
    }
}
fun Any?.customToList(): List<String> {
    return when (this) {
        is String -> listOf(this)
        is Array<*> -> this.mapNotNull { it?.toString() }
        is List<*> -> this.mapNotNull { it?.toString() }
        else -> emptyList()
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



fun getGradientBrushForTier(tier: Int): Brush {

    return when (tier) {
        1 -> Brush.verticalGradient(listOf( Tier1,Color.Red))
        2 -> Brush.verticalGradient(listOf( Tier2,Color.Red))
        3 -> Brush.verticalGradient(listOf( Tier3,Color.Red))
        4 -> Brush.verticalGradient(listOf( Tier4,Color.Red))
        5 -> Brush.verticalGradient(listOf( Tier5,Color.Red))
        6 -> Brush.verticalGradient(listOf( Tier6,Color.Red))
        7 -> Brush.verticalGradient(listOf( Tier7,Color.Red))
        8 -> Brush.verticalGradient(listOf( Tier8,Color.Red))


        // Añade más casos según tus necesidades
        else -> Brush.verticalGradient(listOf(Color.White, Color(128, 128, 128, 128)))
    }
}

fun getColorForTier(tier: Int?): Color {
    return when (tier) {
        1 -> Tier1
        2 -> Tier2
        3 -> Tier3
        4 -> Tier4
        5 -> Tier5
        6 -> Tier6
        7 -> Tier7
        8 -> Tier8
        else -> Color.Black
    }
}

// Función para obtener el texto correspondiente a una propiedad, con manejo de caso desconocido
fun getTextForProperty(property: String): String {
    return Constants.LIST_AERODYNAMICS_TEXTS.getOrDefault(property, Constants.defaultText)
}

// // Función para obtener el texto correspondiente a una propiedad, con manejo de caso desconocido
// Similar a getTextForProperty
fun parsePropertiesToName(property: String): String {
    return when(property) {
        "empty_weight" -> "Peso en Vacio"
        "length" -> "Longitud"
        "max_altitude" -> "Altitud Maxima"
        "max_speed_at_altitude" -> "Velocidad Maxima a "
        "max_takeoff_weight" -> "Peso Maximo al despegue"
        "runway_length_required" -> "Distancia de despegue"
        "turn_time" -> "Tiempo de giro"
        "wing_area" -> "Superficie de Ala"
        "wingspan" -> "Envergadura"
        else -> ""
    }
}

// Función para obtener la unidad de medida correspondiente a una propiedad, con manejo de caso desconocido
fun getUnitForProperty(property: String): String {
    return Constants.LIST_AERODYNAMICS_UNITS.getOrDefault(property, Constants.defaultUnit)
}
fun parseTypeToColor(type:String):Color{
    return when (type) {
        "figther" -> figther
        "stormovik" -> stormovik
        "bomber" -> bomber
        "divebomber" -> divebomber

        else -> Color.Yellow
    }
}


fun getArmyFromType(type: String):String{
    var army:String=""

    // Filtrar por tipo de vehiculo

    when (type) {
        in LIST_TYPE_VEHICLE_AIR-> army= Constants.LIST_TYPE_VEHICLE[1]
        in LIST_TYPE_VEHICLE_TANK-> army= Constants.LIST_TYPE_VEHICLE[0]
        in LIST_TYPE_VEHICLE_NAVAL->army= Constants.LIST_TYPE_VEHICLE[2]
    }
    Log.i("MyTag", "type $type ::$army  ")
    return army
}

/*
#470000
#720c06
#9d190c
#c82513
#f33119
#f66343
#f9946e
#fcc698
#fff7c3

 */