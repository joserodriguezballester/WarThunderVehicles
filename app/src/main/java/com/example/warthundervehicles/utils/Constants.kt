package com.example.warthundervehicles.utils

object Constants {

    const val BASE_URL = "http://wtvehiclesapi.sgambe.serv00.net/"
    val LIST_COUNTRY = listOf(
        "all",
        "usa",
        "germany",
        "ussr",
        "britain",
        "japan",
        "italy",
        "france",
        "sweden",
        "china",
        "israel"
    )
    val LIST_RANK = listOf(1, 2, 3, 4, 5, 6, 7, 8)

    val LIST_TYPE_VEHICLE = listOf(
        "tank",
        "plane",
        "ship",
    )

    val LIST_TYPE_VEHICLE_AIR = listOf(
        "fighter",
        "stormovik",
        "bomber",
        "divebomber"
    )
    val LIST_TYPE_VEHICLE_NAVAL = listOf(
        "torpedoboat",
        "submarinechaser",
        "minelayer",
        "transport",
        "navalferrybarge",
        "destroyer",
        "torpedogunboat",
        "ship",
        "gunboat"
    )
    val LIST_TYPE_VEHICLE_TANK = listOf(
        "lighttank",
        "mediumtank",
        "heavytank",
        "tankdestroyer",
        "spaa"
    )
    val tipoVehicleLists = mapOf(
        "tank" to LIST_TYPE_VEHICLE_TANK,
        "plane" to LIST_TYPE_VEHICLE_AIR,
        "ship" to LIST_TYPE_VEHICLE_NAVAL
    )
    val LIST_PROPERTIES_VEHICLE = listOf(
        "aerodynamics",
        "ammo",
        "engine",
        "lock",
        "losLock",
        "weapons",
        "presets",
        "sensors"
    )
    val LIST_CLASS_VEHICLE = listOf(
        "Aerodynamics",
        "Ammo",
        "Engine",
        "Lock",
        "LosLock",
        "WeaponX",
        "Preset",
        "Sensors"
    )

    val LIST_AERODYNAMICS_PROPERTIES = listOf(
        "emptyWeight",
        "lenght",
        "maxAltitude",
        "maxSpeedAtAlt",
        "maxTakeoffWeight",
        "runwayLenRequired",
        "turnTime",
        "wingArea",
        "wingspan"
    )

    // Crear un listado con los textos correspondientes a cada propiedad
    val LIST_AERODYNAMICS_TEXTS = mapOf(
        "emptyWeight" to "Peso vacío",
        "lenght" to "Longitud",
        "maxAltitude" to "Altitud máxima",
        "maxSpeedAtAlt" to "Velocidad máxima a altitud",
        "maxTakeoffWeight" to "Peso máximo al despegue",
        "runwayLenRequired" to "Longitud de pista requerida",
        "turnTime" to "Tiempo de giro",
        "wingArea" to "Área de alas",
        "wingspan" to "Envergadura"
    )
    val defaultText = "Desconocido"
    // Crear un listado con los unidades correspondientes a cada propiedad
    val LIST_AERODYNAMICS_UNITS = mapOf(
        "emptyWeight" to "kg",
        "lenght" to "m",
        "maxAltitude" to "m",
        "maxSpeedAtAlt" to "m/s",
        "maxTakeoffWeight" to "kg",
        "runwayLenRequired" to "m",
        "turnTime" to "s",
        "wingArea" to "m²",
        "wingspan" to "metros"
    )
    val defaultUnit = "."







}

//***************  INFO **********************//
//    const val IMAGEN_BASE_URL = "http://wtvehiclesapi.sgambe.serv00.net/assets/images/yak-7b.png"
//    const val IMAGEN_TREE_BASE_URL =
//        "http://wtvehiclesapi.sgambe.serv00.net/assets/techtrees/a_7e.png"

//    https://github.com/Sgambe33/WTVehiclesAPI
