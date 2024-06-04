package com.example.warthundervehicles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
import com.example.warthundervehicles.data.repository.DataRepository
import com.example.warthundervehicles.data.repository.LocalRepository
import com.example.warthundervehicles.data.repository.RemoteRepository
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.utils.Constants.LIST_COUNTRY
import com.example.warthundervehicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import toMachine
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository,
 //   private val remoteRepository: RemoteRepository,
//    private val localRepository: LocalRepository
) : ViewModel() {

suspend fun doLocal(){
     dataRepository.useAPIWeb()
}
//    suspend fun doLocal(): Boolean {
//        val localCharacters = localRepository.getMachines()
//        val irAPIWeb=false
//        if (localCharacters.size < 2400 || irAPIWeb) {
//            // obtener todos los vehiculos y pasar a la BD
//            Log.i("MyTag", "RecargaBD")
//            LIST_COUNTRY.drop(1).forEach { country ->
//                for (era in 1..8) {
//                    getVehiclesCountryRank(country, era)
//                }
//            }
//            return false
//        } else {
//            Log.i("MyTag", "No RecargaBD")
//            return true
//        }
//    }
//
//    private suspend fun insertarListaVehiculos(lista: List<RemoteVehicleListItem>) {
//        lista.forEach { vehicle ->
//            Log.e("MyTag", "insertando ${vehicle.identifier}")
//            localRepository.insertMachine(vehicle.toMachine())
//        }
//    }
//
//    private fun getVehiclesCountryRank(countrySelected: String, tierSelected: Int) {
//        viewModelScope.launch {
//            val result: Resource<List<RemoteVehicleListItem>> =
//                remoteRepository.getVehiclesRemoteCountryRank(1000, countrySelected, tierSelected)
//            handler(result)
//        }
//    }
//
//    private suspend fun handler(
//        result: Resource<List<RemoteVehicleListItem>>,
//    ) {
//        when (result) {
//            is Resource.Success -> {
//                Log.i(
//                    "MyTag",
//                    "insertando los vehiculos ${result.data!!.size}  "
//                )
//                insertarListaVehiculos(result.data)
//            }
//
//            is Resource.Error -> {
//                Log.e("MyTag", " Resource.error ${result.message!!}")
//            }
//
//            is Resource.Loading -> {
//                Log.e("MyTag", "Resource.Loading")
//            }
//        }
//    }
}


