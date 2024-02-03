package com.example.warthundervehicles.data.remote.apimodels

data class RemoteVehiclesItem(
    val aerodynamics: Aerodynamics,
    val arcadeBr: Double,
    val costGold: Int,
    val country: String,
    val crewTotalCount: Int,
    val engine: Engine,
    val historicalBr: Double,
    val identifier: String,
    val isGift: Boolean,
    val isPremium: Boolean,
    val mass: Double,
    val presets: List<Preset>,
    val releaseDate: String,
    val repairCostArcadeBasic: Int,
    val repairCostArcadeReference: Int,
    val repairCostHistoricalBasic: Int,
    val repairCostHistoricalReference: Int,
    val repairCostSimulationBasic: Int,
    val repairCostSimulationReference: Int,
    val repairTimeHrsArcade: Double,
    val repairTimeHrsHistorical: Double,
    val repairTimeHrsSimulation: Double,
    val reqExp: Int,
    val sensors: Sensors,
    val simulationBr: Double,
    val tier: Int,
    val train1Cost: Int,
    val train2Cost: Int,
    val train3Cost_exp: Int,
    val train3Cost_gold: Int,
    val type: Any,
    val value: Int,
    val weapons: Any
   // val weapons: List<WeaponX>

)