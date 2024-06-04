import com.example.warthundervehicles.R
import com.example.warthundervehicles.data.local.entities.MachineEntity
import com.example.warthundervehicles.data.remote.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.modelsApp.MachineListItem
//import com.example.warthundervehicles.modelsApp.MachineListItem
import com.example.warthundervehicles.utils.Constants.BEGINurl
import com.example.warthundervehicles.utils.Constants.PREFIXES
import java.util.Locale


fun transformCountry(country: String): Int {
    val banderas = mapOf(
        "all" to R.drawable.ic_bandera_japon,
        "usa" to R.drawable.ic_bandera_usa,
        "germany" to R.drawable.ic_bandera_alemana,
        "ussr" to R.drawable.ic_bandera_urss,
        "britain" to R.drawable.ic_bandera_inglaterra,
        "japan" to R.drawable.ic_bandera_japon,
        "italy" to R.drawable.ic_bandera_italia,
        "france" to R.drawable.ic_bandera_francia,
        "sweden" to R.drawable.ic_bandera_suecia,
        "china" to R.drawable.ic_bandera_china,
        "israel" to R.drawable.ic_bandera_israel
    )
    return banderas[country] ?: 0
}

private fun transformName(identifier: String, prefixes: List<String>): String {
    val modifiedIdentifier = if (prefixes.any { identifier.startsWith(it) }) {
        val prefix = prefixes.first { identifier.startsWith(it) }
        identifier.removePrefix(prefix)
    } else {
        identifier
    }.replace("_", " ")

    return modifiedIdentifier.split(" ").joinToString(" ") {
        it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }
}


fun RemoteVehicleListItem.toVehicleItem(): VehicleItem {
    return VehicleItem(
        aerodynamics = aerodynamics,
        identifier = identifier,
        bandera = transformCountry(country),
        arcadeBr = arcade_br,
        geCost = ge_cost,
        country = country,
        realisticBr = realistic_br,
        name = transformName(identifier, PREFIXES),
        isGift = is_gift,
        isPremium = is_premium,
        //    releaseDate =release_date,
        reqExp = req_exp,
        simulationBr = simulator_br,
        type = vehicle_type,
        value = value,
        imageUrl = BEGINurl + images.image,
        imagen2Url = BEGINurl + images.techtree,
        era = era,
        vel_max = 0,
        weapons= weapons , //JSON nulo
       customizable_presets=customizable_presets,
    )
}

fun NewRemoteVehicle.toVehicle(): VehicleItem {
    return VehicleItem(
        aerodynamics = aerodynamics,
        identifier = identifier,
        bandera = transformCountry(country),
        arcadeBr = arcade_br,
        geCost = ge_cost,
        country = country,
        realisticBr = realistic_br,
        name = transformName(identifier, PREFIXES),
        isGift = is_gift,
        isPremium = is_premium,
        //    releaseDate =release_date,
        reqExp = req_exp,
        simulationBr = simulator_br,
        type = vehicle_type,
        value = value,
        imageUrl = BEGINurl + images.image,
        imagen2Url = BEGINurl + images.techtree,
        era = era,
        vel_max = engine!!.max_speed,
        weapons = weapons,
        customizable_presets = customizable_presets
    )
}
//fun RemoteVehicleListItem.toMachineListItem(): MachineListItem {
//    return MachineListItem(
//        name = transformName(identifier, PREFIXES), // O el campo que desees usar como nombre
//        identifier = identifier,
//        imagen = BEGINurl + images.image, // Ajusta esto a la estructura real de tus datos
//        country = country,
//        rank = era // O el campo que desees usar como rango
//    )
//}

fun VehicleItem.toMachine(): Machine {
    return Machine(
        name = name,
        identifier = identifier,
        imagen = BEGINurl + imageUrl,
        country = country,
        rank = era,
        arcade_br = arcadeBr,
        vehicle_type = type.toString(),
    )
}

fun RemoteVehicleListItem.toMachine(): Machine {
    return Machine(
        name = transformName(identifier, PREFIXES),
        identifier = identifier,
        imagen = BEGINurl + images.image,
        country = country,
        rank = era,
        arcade_br = arcade_br,
        vehicle_type = vehicle_type,
    )
}

fun MachineEntity.toMachine(): Machine {
    return Machine(
        name = transformName(identifier, PREFIXES),
        identifier = identifier,
        imagen =  imagen,
        country = country,
        rank = era,
        arcade_br = arcade_br,
        vehicle_type = vehicle_type,
    )
}

fun Machine.toMachineListItem(): MachineListItem {
    return MachineListItem(
        identifier = identifier,
        bandera = transformCountry(country),
        country = country,
        name = transformName(identifier, PREFIXES),
        imageUrl = imagen,
        era = rank,
        type = vehicle_type,
        arcade_br = arcade_br
    )
}


// val baseUrl = "http://wtvehiclesapi.sgambe.serv00.net/assets/images/"
//val beginUrl = "http://"
//  val imageUrl = baseUrl + vehicle.identifier + ".png"
// imageUrl = "$baseUrl$identifier.png",
