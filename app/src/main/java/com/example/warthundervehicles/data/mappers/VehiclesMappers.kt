import com.example.warthundervehicles.R
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import java.util.Locale



fun transformCountry(country: String): Int {
    val banderas = mapOf(
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

fun RemoteVehiclesItem.toVehicleItem(): VehicleItem {
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

  //  val imageUrl = baseUrl + vehicle.identifier + ".png"

    return VehicleItem(

        aerodynamics=aerodynamics,
        identifier = identifier,
        imageUrl = "$baseUrl$identifier.png",
        bandera=transformCountry(country),
        arcadeBr = arcadeBr,
        costGold = costGold,
        country = country,
        crewTotalCount = crewTotalCount,
        engine=engine,
        historicalBr = historicalBr,
        name = transformName(identifier, prefixes),
        isGift = isGift,
        isPremium = isPremium,
        mass = mass,
        releaseDate = releaseDate,
        repairCostArcadeBasic = repairCostArcadeBasic,
        repairCostArcadeReference = repairCostArcadeReference,
        repairCostHistoricalBasic = repairCostHistoricalBasic,
        repairCostHistoricalReference = repairCostHistoricalReference,
        repairCostSimulationBasic = repairCostSimulationBasic,
        repairCostSimulationReference = repairCostSimulationReference,
        repairTimeHrsArcade = repairTimeHrsArcade,
        repairTimeHrsHistorical = repairTimeHrsHistorical,
        repairTimeHrsSimulation = repairTimeHrsSimulation,
        reqExp = reqExp,
        simulationBr = simulationBr,
        tier = tier,
        train1Cost = train1Cost,
        train2Cost = train2Cost,
        train3Cost_exp = train3Cost_exp,
        train3Cost_gold = train3Cost_gold,
        type = type,
        value = value
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
