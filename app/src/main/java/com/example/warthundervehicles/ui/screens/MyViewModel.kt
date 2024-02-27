package com.example.warthundervehicles.ui.screens


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.data.repository.MyRepository
import com.example.warthundervehicles.utils.Constants
import com.example.warthundervehicles.utils.Constants.LIST_CLASS_VEHICLE
import com.example.warthundervehicles.utils.Constants.LIST_COUNTRY
import com.example.warthundervehicles.utils.Constants.LIST_RANK
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_AIR
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_NAVAL
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_TANK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import toVehicleItem
import javax.inject.Inject
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: MyRepository) : ViewModel() {


    lateinit var nameGrupo: String
    private val _myListaVehiculosRemotos = mutableStateOf<List<RemoteVehiclesItem>>(emptyList())
    val myListaVehiculosRemotos: MutableState<List<RemoteVehiclesItem>> get() = _myListaVehiculosRemotos


    private val _listaVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
    val listaVehiculos: MutableState<List<VehicleItem>> get() = _listaVehiculos

    // solo en getAllVheicles
    private val _listaAllVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
    val listaAllVehiculos: MutableState<List<VehicleItem>> get() = _listaAllVehiculos

    private val _selectedVehicle = mutableStateOf<RemoteVehiclesItem?>(null)
    val selectedVehicle: MutableState<RemoteVehiclesItem?> get() = _selectedVehicle

    private var _selecVehicle = mutableStateOf<VehicleItem?>(null)
    val selecVehicle: MutableState<VehicleItem?> get() = _selecVehicle


    private val _groupedProperties = MutableLiveData<Map<String, List<Pair<String, Any>>>>()
    val groupedProperties: LiveData<Map<String, List<Pair<String, Any>>>> = _groupedProperties

    var showGroupContent by mutableStateOf(false)
    var showVehicleDetails by mutableStateOf(true)

//    val listaNombresVehiculos: List<String>
//        get() = listaVehiculos.value.map { it.name }

    var searchText by mutableStateOf("")

    init {
        Log.e("MyTag", "init****************")
         getAllVehicles()

    }

    fun getAllVehicles() {
        viewModelScope.launch {
            try {
                for (country in LIST_COUNTRY) {
                    for (rank in LIST_RANK) {
                        fetchVehiclesForCountryAndRank(country, rank)
                    }
                }
                _listaAllVehiculos.value = _listaVehiculos.value
                Log.e("MyTag", "All vehicle list ${listaAllVehiculos.value.size}")

            } catch (e: Exception) {
                handleException("Error fetching vehicle list___", e)
            }
        }
    }


    private suspend fun fetchVehiclesForCountryAndRank(country: String, rank: Int) {
        try {
            val response = repository.getAllVehiclesCountryRank(1000, country, rank)
            if (response.isSuccessful) {
                handleSuccessfulResponse(response)
            } else {
                handleErrorResponse(response)
            }
        } catch (e: Exception) {
            handleException("Error fetching vehicles for country: $country, rank: $rank", e)
        }
    }

    private fun <T> handleSuccessfulResponse(response: Response<T>): Unit {
        if (response.isSuccessful) {
            val data = response.body()
            if (data is RemoteVehiclesItem) {
                // Manejar el caso de RemoteVehiclesItem
                _selectedVehicle.value = response.body() as RemoteVehiclesItem
                _selecVehicle.value = _selectedVehicle.value!!.toVehicleItem()
                groupPropertiesByClassAndPrefix(vehicle = selectedVehicle.value!!)
            } else if (data is List<*>) {
                _myListaVehiculosRemotos.value = (response.body()) as List<RemoteVehiclesItem>
                if (_myListaVehiculosRemotos.value.isNotEmpty()) {
                    processVehiclesList()
                } else {
                    Log.e("MyTag", "Empty vehicle list")
                }

            }
        } else {
            Log.e("MyTag", "Manejar el caso de respuesta no exitosa")
            // Manejar el caso de respuesta no exitosa
        }
    }

    private fun handleErrorResponse(response: Response<List<RemoteVehiclesItem>>) {
        Log.e("MyTag", "Error fetching vehicle list. Code: ${response.code()}")
    }

    private fun handleException(message: String, exception: Exception) {
        Log.e("MyTag", message, exception)
    }

    private fun processVehiclesList() {
        for (vehicleRemoto in _myListaVehiculosRemotos.value) {
//            Log.i(
//                "MyTag",
//                "Vehicle pos: ${_myListaVehiculosRemotos.value.indexOf(vehicleRemoto)}," +
//                        " Name: ${vehicleRemoto.identifier}," +
//                        " Country: ${vehicleRemoto.country}"
//            )
            agregarElementoAVehiculos(vehicleRemoto.toVehicleItem())
        }
        _listaVehiculos.value = _listaVehiculos.value.sortedBy { it.arcadeBr }
        Log.i("MyTag", "Total Vehicles: ${listaVehiculos.value.size}")
    }

    // Función para añadir elementos a la lista
    private fun agregarElementoAVehiculos(vehicleItem: VehicleItem) {
        _listaVehiculos.value += listOf(vehicleItem)
    }

    suspend fun getAllVehiclesRank(rank: Int) {
        try {
            val response = repository.getAllVehiclesRank(1000, rank)
            if (response.isSuccessful) {
                handleSuccessfulResponse(response)
            } else {
                handleErrorResponse(response)
            }
        } catch (e: Exception) {
            handleException("Error fetching vehicles for rank:  $rank", e)
        }
    }

    fun limpiarLista() {
        listaVehiculos.value = emptyList<VehicleItem>()
    }

    suspend fun getAllVehiclesCountry(country: String) {
        try {
            val response = repository.getAllVehiclesCountry(1000, country)
            if (response.isSuccessful) {
                handleSuccessfulResponse(response)
            } else {
                handleErrorResponse(response)
            }
        } catch (e: Exception) {
            handleException("Error fetching vehicles for country:  $country", e)
        }
    }

    fun getAllVehiclesArma(silueta: String) {
        //   LIST_TYPE_VEHICLE = listOf("tank", "plane", "ship")
        listaVehiculos.value
        when (silueta) {
            LIST_TYPE_VEHICLE[0] -> {
                for (vehicle in listaVehiculos.value) {
                    if (vehicle.type !in LIST_TYPE_VEHICLE_TANK) {
                        eliminarVehiculo(vehicle)
                    }
                }
            }

            LIST_TYPE_VEHICLE[1] -> {
                for (vehicle in listaVehiculos.value) {
                    if (vehicle.type !in LIST_TYPE_VEHICLE_AIR) {
                        eliminarVehiculo(vehicle)
                    }
                }
            }

            LIST_TYPE_VEHICLE[2] -> {
                for (vehicle in listaVehiculos.value) {
                    if (vehicle.type !in LIST_TYPE_VEHICLE_NAVAL) {
                        eliminarVehiculo(vehicle)
                    }
                }
            }
        }
    }

    // Función para eliminar un vehículo específico
    fun eliminarVehiculo(vehiculoAEliminar: VehicleItem) {
        val listaMutable = _listaVehiculos.value.toMutableList()
        listaMutable.remove(vehiculoAEliminar)
        _listaVehiculos.value = listaMutable
    }

    // Función para eliminar un vehículo por índice
    fun eliminarVehiculoPorIndice(indiceAEliminar: Int) {
        val listaMutable = _listaVehiculos.value.toMutableList()
        listaMutable.removeAt(indiceAEliminar)
        _listaVehiculos.value = listaMutable
    }


    suspend fun getVehicle(identifier: String): Response<RemoteVehiclesItem> {

        try {
            val response = repository.getVehicle(identifier = identifier)
            if (response.isSuccessful) {
                handleSuccessfulResponse(response)
            } else {
                //    handleErrorResponse(response)
                Log.e("MyTag", "Error fetching vehicle: ${response.code()}")
            }
            return response
        } catch (e: Exception) {
            handleException("Error fetching vehicle", e)
            return Response.error(500, "Internal Server Error".toResponseBody(null))
        }
    }


    fun groupPropertiesByClassAndPrefix(vehicle: RemoteVehiclesItem) {
        val groupedMap = mutableMapOf<String, MutableList<Pair<String, Any>>>()

        vehicle.javaClass.kotlin.declaredMemberProperties.forEach { property ->
            val propertyName = property.name
            val propertyValue = property.get(vehicle)

            // Agrupar por clase
            //   val className = propertyValue?.javaClass?.simpleName ?: "Other"
            if (propertyName in Constants.LIST_PROPERTIES_VEHICLE)
                groupedMap.getOrPut(propertyName) { mutableListOf() }
                    .add((propertyName to propertyValue) as Pair<String, Any>)

            // Agrupar por prefijo "train" o "repair"
            if (propertyName.startsWith("train")) {
                groupedMap.getOrPut("Train") { mutableListOf() }
                    .add((propertyName to propertyValue) as Pair<String, Any>)
            }
            // Agrupar por prefijo "train" o "repair"
            if (propertyName.startsWith("repair")) {
                groupedMap.getOrPut("Repair") { mutableListOf() }
                    .add((propertyName to propertyValue) as Pair<String, Any>)
            }
            // Agrupar por características en el nombre (por ejemplo, "Br")
            if (propertyName.contains("Br")) {
                groupedMap.getOrPut("BR") { mutableListOf() }
                    .add((propertyName to propertyValue) as Pair<String, Any>)
            }
        }
        _groupedProperties.value = groupedMap
    }

    fun isGrupoVacio(propertyValue: Any): Boolean {
        return (propertyValue is Collection<*> && propertyValue.isEmpty())
    }

    fun isAllPropertiesNullOrZeroOrFalse(propertyValue: Any): Boolean {
        return LIST_CLASS_VEHICLE.any { it == propertyValue::class.simpleName } &&
                processProperties(propertyValue)
    }

    // Función genérica para procesar propiedades de cualquier clase
    private fun processProperties(propertyValue: Any): Boolean {
        val properties = propertyValue::class.members
            .filterIsInstance<KProperty1<Any, Any?>>()

        return properties.all { property ->
            val value = property.get(propertyValue)
            value == null || value == 0.0 || value == false
        }
    }

    val listaFiltradaVehiculos = mutableStateOf(listOf<VehicleItem>())

    // Obtener la lista filtrada según el texto de búsqueda
    fun getFilteredListBySearch() {
        listaFiltradaVehiculos.value = listaVehiculos.value.filter { vehiculo ->
            vehiculo.name.contains(searchText, ignoreCase = true)
        }
    }

    // Obtener la lista filtrada según la condición arcadeRB
    fun getFilteredListByArcadeRB(vehiculoItem: VehicleItem) {

        // Determinar la lista específica según vehiculoItem
        val listaEspecifica = when (vehiculoItem.type) {
           in LIST_TYPE_VEHICLE_AIR -> LIST_TYPE_VEHICLE_AIR
           in LIST_TYPE_VEHICLE_TANK -> LIST_TYPE_VEHICLE_TANK
           in LIST_TYPE_VEHICLE_NAVAL -> LIST_TYPE_VEHICLE_NAVAL
            else -> {
                Log.i("MyTag", "falta este tipo ${vehiculoItem.type}")
                emptyList()
            }
        }
        Log.i("MyTag", "lista: $listaEspecifica")
        Log.i("MyTag", "lista sin filtrar: ${listaFiltradaVehiculos.value.size}")
        // Filtrar los vehículos según la lista específica y la condición de ArcadeBR
        listaFiltradaVehiculos.value = listaVehiculos.value.filter { vehiculo ->

            val arcadeInRange =
                vehiculo.arcadeBr in (vehiculoItem.arcadeBr - 1)..(vehiculoItem.arcadeBr + 1)
            val mismoTipo = vehiculo.type in listaEspecifica
            Log.i("MyTag", "evaluando...: ${vehiculo.arcadeBr}-$arcadeInRange ")
            Log.i("MyTag", "evaluando...: ${vehiculo.type}-$mismoTipo ")
            arcadeInRange && mismoTipo

        }
        Log.i("MyTag", "lista filtrada: ${listaFiltradaVehiculos.value.size}")
    }
}


