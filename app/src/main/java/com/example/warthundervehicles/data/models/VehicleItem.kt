package com.example.warthundervehicles.data.models

import com.example.warthundervehicles.data.remote.apimodels.Aerodynamics
import com.example.warthundervehicles.data.remote.apimodels.Engine

data class VehicleItem(
    val aerodynamics: Aerodynamics,
    val identifier: String,
    val imageUrl:String,
    val bandera:Int,
    val arcadeBr: Double,
    val costGold: Int,
    val country: String,
    val crewTotalCount: Int,
    val engine: Engine,
    val historicalBr: Double,
    val name: String,
    val isGift: Boolean,
    val isPremium: Boolean,
    val mass: Double,
  //  val presets: List<PresetX>,
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
    val simulationBr: Double,
    val tier: Int,
    val train1Cost: Int,
    val train2Cost: Int,
    val train3Cost_exp: Int,
    val train3Cost_gold: Int,
    val type: Any,
    val value: Int,
//    val weapons: List<WeaponXX>
)