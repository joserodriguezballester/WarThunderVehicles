

import com.example.warthundervehicles.data.remote.apimodels.version2.Aerodynamics
import com.example.warthundervehicles.data.remote.apimodels.version2.vehicle.Engine
import com.example.warthundervehicles.data.remote.apimodels.version2.vehicle.Images
import com.example.warthundervehicles.data.remote.apimodels.version2.vehicle.WeaponX
import java.util.Date

data class NewRemoteVehicle(
    val aerodynamics: Aerodynamics?, // JSON nulo
    val arcade_br: Double, //
    val country: String, //
    val crew_total_count: Int, // ¿que es?

    val engine: Engine?, //(JSON)
    val era: Int, //
    val event: String?, // (Any)
    val ge_cost: Int, //
    val has_customizable_weapons: Boolean,//
    val identifier: String, //
    val images: Images,
    val is_gift: Boolean, //
    val is_premium: Boolean, //
    val mass: Double, //
    val realistic_br: Double, //
    val repair_cost_arcade: Int,//
    val repair_cost_full_upgraded_arcade: Int,//
    val repair_cost_full_upgraded_realistic: Int,//
    val repair_cost_full_upgraded_simulator: Int,//
    val repair_cost_per_min_arcade: Int,//
    val repair_cost_per_min_realistic: Int,//
    val repair_cost_per_min_simulator: Int,//
    val repair_cost_realistic: Int,
    val repair_cost_simulator: Int,
    val repair_time_arcade: Double, //
    val repair_time_no_crew_arcade: Double, //
    val repair_time_no_crew_realistic: Double,//
    val repair_time_no_crew_simulator: Double,//
    val repair_time_realistic: Double,//
    val repair_time_simulator: Double,//
    val req_exp: Int, //
    val required_vehicle: String?, //nulo
    val simulator_br: Double,// Int
    val train1_cost: Int, //
    val train2_cost: Int, //
    val train3_cost_exp: Int, //
    val train3_cost_gold: Int, //
    val value: Int, //
    val vehicle_type: String, //
    val version: String?,
    val versions: List<String>, //
 //   val versions: List<String>,
 //   val weapons: List<WeaponX>? //JSON nulo
 //   val modifications: List<Modification>?, //(JSON)
 //   val presets: List<Preset>?, //Json nulo
  //   val customizable_presets: List<Any>?,
    val release_date: Any?, // Date

    val customizable_presets: Any?,
    val weapons: List<WeaponX>?, //JSON nulo
    val modifications: Any?, //(JSON)
    val presets: Any?, //Json nulo






)