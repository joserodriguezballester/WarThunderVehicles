package com.example.warthundervehicles.data.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.local.entities.MachineEntity
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
import com.example.warthundervehicles.modelsApp.Machine
//import com.example.warthundervehicles.modelsApp.MachineListItem
import com.example.warthundervehicles.utils.Constants
import com.example.warthundervehicles.utils.Resource
import kotlinx.coroutines.launch
import toMachine
//import toMachineListItem
import javax.inject.Inject


class DataRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {

    private var useAPI: Boolean = false
    suspend fun useAPIWeb() {
        val irAPIWeb = false
        val localCharacters = localRepository.getMachines()
        if (irAPIWeb) {
            //   if (localCharacters.size < 2400 || irAPIWeb) {
            // obtener todos los vehiculos y pasar a la BD
            Log.i("MyTag", "RecargaBD.... useAPI ->true")
            useAPI = true
            Constants.LIST_COUNTRY.drop(1).forEach { country ->
                //    Log.i("MyTag", "RecargaBD....country $country")
                for (era in 1..8) {
                    Log.i("MyTag", "RecargaBD..country $country..era $era")
                    getVehiclesCountryRank(country, era)
                }
            }
        } else {
            Log.i("MyTag", "No RecargaBD  .... useAPI ->false")
            useAPI = false
        }
    }

    suspend fun getVehiclesCountryRank(
        countrySelected: String,
        tierSelected: Int
    ): Resource<List<Machine>> {
        Log.i("MyTag", "getVehiclesCountryRank useAPI ${useAPI} ")
        if (useAPI) {
            Log.i("MyTag", "obteniendo de la api useAPI ${useAPI} ")
//            // Obtener datos de la API
            val result1: Resource<List<RemoteVehicleListItem>> =
                remoteRepository.getVehiclesRemoteCountryRank(1000, countrySelected, tierSelected)
            return handler(result1)

        } else {
            Log.i("MyTag", "obteniendo de local useAPI ${useAPI} ")
            // Obtener datos de la base de datos local
            val result: Resource<List<MachineEntity>> =
                localRepository.getVehiclesLocalCountryRank(countrySelected, tierSelected)
            return handler2(result)
            // return result
        }
    }


    private suspend fun handler(result: Resource<List<RemoteVehicleListItem>>): Resource<List<Machine>> {
        return when (result) {
            is Resource.Success -> {
                Log.i(
                    "MyTag",
                    "insertando los vehiculos ${result.data!!.size}  "
                )
                insertarListaVehiculos(result.data)
                Resource.Success(result.data.map { it.toMachine() })
            }

            is Resource.Error -> {
                Log.e("MyTag", " Resource.error ${result.message!!}")
                Resource.Error(result.message!!)
            }

            is Resource.Loading -> {
                Log.e("MyTag", "Resource.Loading")
                //TODO LOADING
                Resource.Error(result.message!!)
            }
        }
    }

    private suspend fun handler2(result: Resource<List<MachineEntity>>): Resource<List<Machine>> {
        Log.i("MyTag", "leyendo de BD ...  ")
        return when (result) {
            is Resource.Success -> {
                Log.i(
                    "MyTag",
                    "leyendo de BD ${result.data!!.size}  "
                )
                Resource.Success(result.data.map { it.toMachine() })

            }

            is Resource.Error -> {
                Log.e("MyTag", " Resource.error ${result.message!!}")
                Resource.Error(result.message!!)
            }

            is Resource.Loading -> {
                Log.e("MyTag", "Resource.Loading")
                //TODO LOADING
                Resource.Error(result.message!!)
            }
        }
    }


    suspend fun insertarListaVehiculos(data: List<RemoteVehicleListItem>) {
        data.forEach { vehicle ->
            Log.e("MyTag", "insertando ${vehicle.identifier}")
            localRepository.insertMachine(vehicle.toMachine())
        }
    }

}
