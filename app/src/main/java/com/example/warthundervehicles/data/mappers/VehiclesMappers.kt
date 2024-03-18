import com.example.warthundervehicles.R
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.version2.Engine
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
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
//
fun RemoteVehicleListItem.toVehicleItem(): VehicleItem {
    val prefixes = listOf(
        "ussr_destroyer_",
        "ussr_cruiser_",
        "ussr_battleship_",
        "ussr_battlecruiser_",
        "ussr_",
        "us_",
        "germ_",
        "uk_",
        "jp_",
        "it_",
        "fr_",
        "sw_",
        "il_"
    )
    val baseUrl =
        "http://wtvehiclesapi.sgambe.serv00.net/assets/images/"
    val beginUrl= "http://"
  //  val imageUrl = baseUrl + vehicle.identifier + ".png"

    return VehicleItem(
        aerodynamics =aerodynamics,
       identifier = identifier,
    //    imageUrl = "$baseUrl$identifier.png",
        bandera =transformCountry(country),
        arcadeBr = arcade_br,
        geCost = ge_cost,
        country = country,
        realisticBr = realistic_br,
        name = transformName(identifier, prefixes),
        isGift = is_gift,
        isPremium = is_premium,
    //    releaseDate =release_date,
        reqExp = req_exp,
        simulationBr = simulator_br,
        type = vehicle_type,
        value = value,
       // imageUrl = "$baseUrl$identifier.png",
        imageUrl = beginUrl+ images.image,
        imagen2Url = beginUrl+ images.techtree,
        era =era,
        vel_max = 0,
    )
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

fun NewRemoteVehicle.toVehicle():VehicleItem{
    val prefixes = listOf(
        "ussr_destroyer_",
        "ussr_cruiser_",
        "ussr_battleship_",
        "ussr_battlecruiser_",
        "ussr_",
        "us_",
        "germ_",
        "uk_",
        "jp_",
        "it_",
        "fr_",
        "sw_",
        "il_"
    )
    val baseUrl =
        "http://wtvehiclesapi.sgambe.serv00.net/assets/images/"
    val beginUrl= "http://"
    //  val imageUrl = baseUrl + vehicle.identifier + ".png"

    return VehicleItem(
        aerodynamics =aerodynamics,
        identifier = identifier,
        //    imageUrl = "$baseUrl$identifier.png",
        bandera =transformCountry(country),
        arcadeBr = arcade_br,
        geCost = ge_cost,
        country = country,
        realisticBr = realistic_br,
        name = transformName(identifier, prefixes),
        isGift = is_gift,
        isPremium = is_premium,
        //    releaseDate =release_date,
        reqExp = req_exp,
        simulationBr = simulator_br,
        type = vehicle_type,
        value = value,
        // imageUrl = "$baseUrl$identifier.png",
        imageUrl = beginUrl+ images.image,
        imagen2Url = beginUrl+ images.techtree,
        era =era,
        vel_max = engine!!.max_speed,
    )
}