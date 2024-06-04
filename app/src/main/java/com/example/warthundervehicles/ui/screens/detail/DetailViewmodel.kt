package com.example.warthundervehicles.ui.screens.detail

import NewRemoteVehicle
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.repository.DataRepository
import com.example.warthundervehicles.data.repository.LocalRepository

import com.example.warthundervehicles.data.repository.RemoteRepository
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.utils.Constants.LIST_COUNTRY
import com.example.warthundervehicles.utils.Resource
import com.example.warthundervehicles.utils.getArmyFromType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel()
class DetailViewmodel @Inject constructor(
    private val dataRepository: DataRepository,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) : ViewModel() {


    private val _selectedVehicle = mutableStateOf<NewRemoteVehicle?>(null)
    val selectedVehicle: MutableState<NewRemoteVehicle?> get() = _selectedVehicle

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    suspend fun getVehicle(identifier: String): Resource<NewRemoteVehicle> {
        return try {
            val result: Resource<NewRemoteVehicle> = remoteRepository.getVehicle(identifier)
            when (result) {
                is Resource.Success -> {
                    Log.e("MyTag", "Resource.Success ${result.data!!}")
                    _selectedVehicle.value = result.data!!
                }

                is Resource.Error -> {
                    Log.e("MyTag", " Resource.error ${result.message!!}")
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    Log.e("MyTag", "Resource.Loading")
                }
            }
            result
        } catch (e: Exception) {
            // Manejo de excepciones si es necesario
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    fun VehiculosEnemigosImagen(
        vehiculo: NewRemoteVehicle,
        onListaVehiculosReady: (List<String>) -> Unit
    ) {
        Log.i("MyTag", " VehiculosEnemigosImagen:")
        val limInfBr: Double = (vehiculo.arcade_br) - 1
        val limSupBr: Double = (vehiculo.arcade_br) + 1
        // Buscar la lista
        val limInfRank: Int = if (vehiculo.era > 1) vehiculo.era - 1 else 1
        val limSupRank: Int = if (vehiculo.era < 8) vehiculo.era + 1 else 8

        val ranks = if ((limSupRank - limInfRank) == 0)
            listOf<Int>(limInfRank, limSupRank)
        else
            listOf<Int>(limInfRank, limInfRank + 1, limSupRank)

        val preListaVehiculos = mutableListOf<Machine>()
        val listaVehiculos = mutableListOf<Machine>()

        val type = vehiculo.vehicle_type

        // Realiza las consultas para cada país y rango
        viewModelScope.launch {
            try {
                LIST_COUNTRY.forEach { country ->
                    ranks.forEach { rank ->
                        val result = dataRepository.getVehiclesCountryRank(country, rank)
                        when (result) {
                            is Resource.Success -> {
                                preListaVehiculos.addAll(result.data!!)

                                preListaVehiculos.forEach {
                                    if (it.vehicle_type == type) {
                                        listaVehiculos.add(it)
                                    }
                                }

                                //   listaVehiculos.addAll(result.data!!)
                            }

                            is Resource.Error -> {
                                // Maneja el error si es necesario
                                loadError.value = result.message!!
                                isLoading.value = false
                            }

                            is Resource.Loading -> {
                                // Puedes hacer algo si lo necesitas durante la carga
                            }
                        }
                    }
                }
                // Mapea los nombres de los vehículos
          //      val listaNombresVehiculos = listaVehiculos.map { it.identifier }
                val listaNombresVehiculos = listaVehiculos.map { it.identifier}
                Log.i("MyTags", " listaNombresVehiculosr::${listaNombresVehiculos}")
                Log.i("MyTags", " listaVehiculos::${listaVehiculos}")

                // Llama al callback con la lista de nombres de vehículos
                onListaVehiculosReady(listaNombresVehiculos)
            } catch (e: Exception) {
                // Maneja cualquier excepción que pueda ocurrir
                loadError.value = e.message ?: "Error desconocido"
                isLoading.value = false
            }
        }
    }


    fun VehiculosEnemigos(
        vehiculo: NewRemoteVehicle,
        onListaVehiculosReady: (List<String>) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val limInfBr: Double = (vehiculo.arcade_br) - 1
                val limSupBr: Double = (vehiculo.arcade_br) + 1
                val limInfRank: Int = if (vehiculo.era > 1) vehiculo.era - 1 else 1
                val limSupRank: Int = if (vehiculo.era < 8) vehiculo.era + 1 else 8
                val ranks = if ((limSupRank - limInfRank) == 0) listOf(limInfRank, limSupRank)
                else listOf(limInfRank, limInfRank + 1, limSupRank)
                val preListaVehiculos = mutableListOf<Machine>()
                val listaVehiculos = mutableListOf<Machine>()
                val type = getArmyFromType(vehiculo.vehicle_type)

                withContext(Dispatchers.IO) {
                    LIST_COUNTRY.forEach { country ->
                        ranks.forEach { rank ->
                            val result = dataRepository.getVehiclesCountryRank(country, rank)
                            when (result) {
                                is Resource.Success -> {
                                    preListaVehiculos.addAll(result.data!!)
                                }

                                is Resource.Error -> {
                                    // Maneja el error si es necesario
                                }

                                is Resource.Loading -> {
                                    // Puedes hacer algo si lo necesitas durante la carga
                                }
                            }
                        }
                    }

                    // Filtrar vehículos por tipo
                    preListaVehiculos.filterTo(listaVehiculos) {
                        (getArmyFromType(it.vehicle_type) == type)
                                &&
                                (it.arcade_br in ((limInfBr)..(limSupBr)))
                    }
                }

                // Mapear los nombres de los vehículos
                val listaNombresVehiculos = listaVehiculos.map { it.identifier}


                // Llama al callback con la lista de nombres de vehículos en el hilo principal
                withContext(Dispatchers.Main) {
                    Log.i("MyTags", " listaNombresVehiculosr::${listaNombresVehiculos}")
                    Log.i("MyTags", " listaVehiculos::${listaVehiculos}")
                    Log.w("MyTags", "Lista::: ${listaNombresVehiculos.size}")
                    onListaVehiculosReady(listaNombresVehiculos)

                }


            } catch (e: Exception) {
                // Maneja cualquier excepción que pueda ocurrir
            }
        }
    }

}


//    fun getVehicle(identifier: String) {
//        viewModelScope.launch {
//            val result: Resource<NewRemoteVehicle> = repository.getVehicle(identifier)
//            when (result) {
//                is Resource.Success -> {
//                    Log.e("MyTag", "Resource.Success ${result.data!!}")
//                    _selectedVehicle.value = result.data!!
//
//                }
//
//                is Resource.Error -> {
//                    Log.e("MyTag", " Resource.error ${result.message!!}")
//                    loadError.value = result.message!!
//                    isLoading.value = false
//
//                }
//                is Resource.Loading -> {
//                    Log.e("MyTag", "Resource.Loading")
//
//                }
//
//
//            }
//
//        }
//
//    }