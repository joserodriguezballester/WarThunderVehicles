package com.example.warthundervehicles.ui.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.local.dao.MachineDao
import com.example.warthundervehicles.data.remote.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
import com.example.warthundervehicles.data.repository.LocalRepository
import com.example.warthundervehicles.data.repository.RemoteRepository
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.utils.Constants.LIST_COUNTRY
import com.example.warthundervehicles.utils.Constants.tipoVehicleLists
import com.example.warthundervehicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import toVehicleItem

import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repository: RemoteRepository,
    private val machineDao: MachineDao,
 //  private val localBD:Boolean
) : ViewModel() {
    val machineRepository = LocalRepository(machineDao)

    private val _myListaVehiculosRemotos = mutableStateOf<List<RemoteVehicleListItem>>(emptyList())
    private val _listaVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
    val listaVehiculos: MutableState<List<VehicleItem>> get() = _listaVehiculos

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    var tierSelected: Int = 0
    var countrySelected: String = "all"
    var armySelected: String = "all"

    init {
      //    getAllVehicles()
        getVehicles()
    //    Log.i("MyTag","hoooollaa $localBD")
    }

    fun getVehicles() {
        viewModelScope.launch {
            val result: Resource<List<RemoteVehicleListItem>> = repository.getVehiclesList(1000)
            handler(result)
        }
    }

    fun inserMachines(machine:Machine){
        viewModelScope.launch {
            machineRepository.insertMachine(machine = machine)
        }
    }

    private fun getVehiclesCountryRank(countrySelected: String, tierSelected: Int) {
        viewModelScope.launch {
            val result: Resource<List<RemoteVehicleListItem>> =
                repository.getVehiclesRemoteCountryRank(1000, countrySelected, tierSelected)
            handler(result)
        }

    }

    fun clickedGetVehiclesRank(era: Int) {
        Log.i("MyTag", "clickedGetVehiclesRank $era , $countrySelected")
        if (era == 0) {
            for (tier in 1..8) {
                Log.e("MyTag", "tier: $tier,")
                getVehiclesRank(tier)
            }
        } else
        // un rank concreto
            getVehiclesRank(era)
    }

    private fun getVehiclesRank(era: Int) {
        viewModelScope.launch {
            val result: Resource<List<RemoteVehicleListItem>> =
                repository.getAllVehiclesRank(1000, era)
            handler(result)
        }
    }


    private fun processListRemoteToListLocal() {
        for (vehicleRemoto in _myListaVehiculosRemotos.value) {
            addVehicleToList(vehicleRemoto.toVehicleItem())
        }
        //  ordenarListaPorBR()
        Log.i("MyTag", "Total Vehicles: ${listaVehiculos.value.size}")
    }

    private fun ordenarListaPorBR() {
        _listaVehiculos.value = _listaVehiculos.value.sortedByDescending { it.arcadeBr }
    }

    // Función para añadir elementos a la lista
    private fun addVehicleToList(vehicleItem: VehicleItem) {
        _listaVehiculos.value += listOf(vehicleItem)
    }


    fun limpiarLista() {
        listaVehiculos.value = emptyList<VehicleItem>()
        Log.i("MyTag", "limpiarLista  ${listaVehiculos.value}")
    }

    fun clickedGetVehiclesCountry(country: String) {
        Log.i("MyTag", "tierSelected $tierSelected")
        if (country != "all") {
            getVehiclesCountry(country)
        } else {
            for (it in LIST_COUNTRY.drop(1)) {
                getVehiclesCountry(it)
            }
        }

    }

    private fun getVehiclesCountry(country: String) {
        viewModelScope.launch {
            val result: Resource<List<RemoteVehicleListItem>> =
                if (tierSelected == 0) {
                    repository.getAllVehiclesCountry(country = country)
                } else {
                    repository.getLimitVehiclesCountryRank(
                        1000,
                        country = country,
                        rank = tierSelected
                    )
                }
            handler(result)
        }
    }

    fun clickedGetVehiclesArmy(army: String) {
        Log.i("MyTag", "clickedGetVehiclesArmy $army")
        if (army != "all") {
            getVehiclesArmy(army)
        }
//        else {
//            for (army in LIST_TYPE_VEHICLE) {
//                getVehiclesArmy(army)
//            }
//        }

    }

    private fun getVehiclesArmy(army: String) {

        viewModelScope.launch {
            val selectedTier: List<String> =
                if (tierSelected != 0) listOf(tierSelected.toString()) else
                    listOf("1", "2", "3", "4", "5", "6", "7", "8")
            val selectedCountry =
                if (countrySelected != "all") listOf(countrySelected) else LIST_COUNTRY.drop(1)

            for (tier in selectedTier) {
                Log.i("MyTag", "tier $tier ")
                for (country in selectedCountry) {
                    Log.i("MyTag", "country $country ")
                    val rank = tier.toInt()
                    todosTypes(army, country, rank)
                }
            }
        }
    }

    private suspend fun todosTypes(army: String, country: String, rank: Int) {
        val distintosTypes = tipoVehicleLists[army]
        for (subArmy in distintosTypes!!) {
            Log.e(
                "MyTag",
                "Tipo de vehículo subArmy: $subArmy :$country : $rank"
            )
            val result: Resource<List<RemoteVehicleListItem>> =
                repository.getVehiclesArmyCountryRank(
                    army = subArmy, country = country, rank = rank
                )
            handler(result)
        }
    }

    private fun handler(result: Resource<List<RemoteVehicleListItem>>) {
        when (result) {
            is Resource.Success -> {
                Log.e("MyTag", "Resource.Success ${result.data!!}")
                _myListaVehiculosRemotos.value = result.data!!
                processListRemoteToListLocal()
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
    }
}

