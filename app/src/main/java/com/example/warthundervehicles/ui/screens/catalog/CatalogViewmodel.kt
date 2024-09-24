package com.example.warthundervehicles.ui.screens.catalog

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
import com.example.warthundervehicles.data.remote.models.VehicleItem
import com.example.warthundervehicles.data.repository.DataRepository
import com.example.warthundervehicles.data.repository.LocalRepository
import com.example.warthundervehicles.data.repository.RemoteRepository
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.modelsApp.MachineListItem
import com.example.warthundervehicles.utils.Constants
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_AIR
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_NAVAL
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE_TANK
import com.example.warthundervehicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import toMachineListItem
import toVehicleItem
import javax.inject.Inject

@HiltViewModel
class CatalogViewmodel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val _listaVehiculos = mutableStateOf<List<MachineListItem>>(emptyList())
    val listaVehiculos: MutableState<List<MachineListItem>> get() = _listaVehiculos

    private val _listaTank = mutableStateOf<List<MachineListItem>>(emptyList())
    val listaTank: MutableState<List<MachineListItem>> get() = _listaTank

    private val _listNaval = mutableStateOf<List<MachineListItem>>(emptyList())
    val listNaval: MutableState<List<MachineListItem>> get() = _listNaval

    private val _listAir = mutableStateOf<List<MachineListItem>>(emptyList())
    val listAir: MutableState<List<MachineListItem>> get() = _listAir


    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    val listArma = mutableListOf<String>()

    fun PedirVehiculos(list_countries: List<String>, list_tiers: List<Int>) {
        list_countries.forEachIndexed { countryIndex, country ->
            list_tiers.forEachIndexed { tierIndex, tier ->
                Log.e(
                    "MyTag",
                    "CatalogViewmodel country [$countryIndex]: $country, tier [$tierIndex]: $tier"
                )
                getVehiclesCountryRank(country, tier)
            }
        }
    }

    private fun getVehiclesCountryRank(countrySelected: String, tierSelected: Int) {
        viewModelScope.launch {
            val result: Resource<List<Machine>> =
                //  repository.getVehiclesRemoteCountryRank(1000, countrySelected, tierSelected)
                dataRepository.getVehiclesCountryRank(countrySelected, tierSelected)
            handler(result)
        }
    }

    private fun handler(result: Resource<List<Machine>>) {
        val listaVehiculosRemotos = mutableStateOf<List<Machine>>(emptyList())
        when (result) {
            is Resource.Success -> {
                Log.e("MyTag", "Resource.Success ${result.data!!}")
                processResultToListLocal(result.data)
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

    private fun processResultToListLocal(machineList: List<Machine>) {
        for (machine in machineList) {
            when (machine.vehicle_type) {
                in LIST_TYPE_VEHICLE_TANK -> addVehicleToList(machine, _listaTank)
                in LIST_TYPE_VEHICLE_NAVAL -> addVehicleToList(machine, _listNaval)
                in LIST_TYPE_VEHICLE_AIR -> addVehicleToList(machine, _listAir)
            }
            Log.i("MyTag", "Total Vehicles handler: ${listaVehiculos.value.size}")
        }
    }

    private fun addVehicleToList(
        machine: Machine,
        listToUpdate: MutableState<List<MachineListItem>>
    ) {
        val updatedList = listToUpdate.value.toMutableList()
        updatedList += machine.toMachineListItem()
        listToUpdate.value = updatedList

        viewModelScope.launch {
            mostrarArmamento(machine.identifier)
        }

    }

    private fun processListRemoteToListLocal(listaVehiculosRemotos: MutableState<List<Machine>>) {
        for (machine in listaVehiculosRemotos.value) {
            addVehicleToList(machine.toMachineListItem())
        }
        Log.i("MyTag", "Total Vehicles: ${listaVehiculos.value.size}")
    }

    // Función para añadir elementos a la lista
    private fun addVehicleToList(machine: MachineListItem) {
        Log.i("MyTag", "addVehicleToList***********")

        _listaVehiculos.value += listOf(machine)
        viewModelScope.launch {
            mostrarArmamento(machine.identifier)
        }
    }

    private suspend fun mostrarArmamento(identifier: String) {
        Log.i("MyTag", "mostrarArmamentot***********")

        val result = remoteRepository.getVehicle(identifier = identifier)
        when (result) {
            is Resource.Success -> {
                val machine = result.data
                if (machine != null) {

                    //     Log.e("MyTagz", "machine ${machine.identifier} ${machine.weapons}")
                    machine.weapons?.forEachIndexed { index, weapon ->
                        Log.i("Mytagz", "**-** ${machine.weapons[index].name}")
//                        Log.i(
//                            "Mytagz",
//                            "**** ${weapon.count} ${weapon.weapon_type} ${weapon.ammos[0].caliber * 1000} mm"
//                        )
//
//                        // Comprobación adicional para evitar nulos
                        val weaponName = machine.weapons[index].name
                        if (weaponName !in listArma) {
                            listArma.add(weaponName)
                            Log.i("Mytagz", "Añadido a listArma: $weaponName")
                        } else {
                            Log.i("Mytagz", "Ya está en listArma: $weaponName")
                        }
                    }
                } else {
                    Log.i("Mytagz", "Machine es nulo")
                }
                Log.i("Mytagz", "listArma**-** ${listArma}")


//
//                Log.i("Mytagz", "listArma**-** ${listArma}")
//
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

