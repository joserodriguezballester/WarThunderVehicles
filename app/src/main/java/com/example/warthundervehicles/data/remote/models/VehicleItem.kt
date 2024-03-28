package com.example.warthundervehicles.data.remote.models

import com.example.warthundervehicles.data.remote.apimodels.version2.Aerodynamics


data class VehicleItem(
    val aerodynamics: Aerodynamics?,
    val identifier: String,
    val imageUrl: String,
    val imagen2Url:String,
    val bandera: Int,
    val arcadeBr: Double,
    val country: String,
    val name: String,
    val isGift: Boolean,
    val isPremium: Boolean,
    val reqExp: Int,
    val type: Any,
    val value: Int,
    val geCost: Int,
    val realisticBr: Double,
  //  val releaseDate: String,
    val simulationBr: Double,
    val era:Int,
    val vel_max:Int

)