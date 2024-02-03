package com.example.warthundervehicles.ui.screens


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.data.repository.MyRepository
import com.example.warthundervehicles.utils.Constants.LIST_COUNTRY
import com.example.warthundervehicles.utils.Constants.LIST_RANK
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_AIR
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_NAVAL
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_TANK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import toVehicleItem
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: MyRepository) : ViewModel() {


    private val _myListaVehiculosRemotos = mutableStateOf<List<RemoteVehiclesItem>>(emptyList())
    val myListaVehiculosRemotos: MutableState<List<RemoteVehiclesItem>> get() = _myListaVehiculosRemotos


    private val _listaVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
    val listaVehiculos: MutableState<List<VehicleItem>> get() = _listaVehiculos

    // solo en getAllVheicles
    private val _listaAllVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
    val listaAllVehiculos: MutableState<List<VehicleItem>> get() = _listaAllVehiculos

    init {
        //  fetchVehiclesForCountryAndRank()
    //    getAllVehicles()
    }

    private fun getAllVehicles() {
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

    private fun handleSuccessfulResponse(response: Response<List<RemoteVehiclesItem>>) {
        _myListaVehiculosRemotos.value = response.body() ?: emptyList()
        if (_myListaVehiculosRemotos.value.isNotEmpty()) {
            processVehiclesList()
        } else {
            Log.e("MyTag", "Empty vehicle list")
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
}
