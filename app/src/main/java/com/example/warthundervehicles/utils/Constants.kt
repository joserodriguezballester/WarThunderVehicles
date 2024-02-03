package com.example.warthundervehicles.utils

object Constants {

    const val BASE_URL = "http://wtvehiclesapi.sgambe.serv00.net/"
    val LIST_COUNTRY = listOf(
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
        "ship"
    )
    val LIST_TYPE_VEHICLE_TANK = listOf(
        "lighttank",
        "mediumtank",
        "heavytank",
        "tankdestroyer",
        "spaa"
    )
    const val IMAGEN_BASE_URL = "http://wtvehiclesapi.sgambe.serv00.net/assets/images/yak-7b.png"
    const val IMAGEN_TREE_BASE_URL =
        "http://wtvehiclesapi.sgambe.serv00.net/assets/techtrees/a_7e.png"


   // https://github.com/Sgambe33/WTVehiclesAPI
}