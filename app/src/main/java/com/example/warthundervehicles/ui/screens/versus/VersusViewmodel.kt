package com.example.warthundervehicles.ui.screens.versus

import NewRemoteVehicle
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.warthundervehicles.data.repository.DataRepository
import com.example.warthundervehicles.data.repository.LocalRepository
import com.example.warthundervehicles.data.repository.RemoteRepository
import com.example.warthundervehicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VersusViewmodel @Inject constructor(
    private val dataRepository: DataRepository,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) : ViewModel() {

    private val _miVehicle = mutableStateOf<NewRemoteVehicle?>(null)
    val miVehicle: MutableState<NewRemoteVehicle?> get() = _miVehicle

    private val _vehicleRival = mutableStateOf<NewRemoteVehicle?>(null)
    val vehicleRival: MutableState<NewRemoteVehicle?> get() = _vehicleRival

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    suspend fun getVehicle(identifier: String, order: Int): Resource<NewRemoteVehicle> {
        return try {
            val result: Resource<NewRemoteVehicle> = remoteRepository.getVehicle(identifier)
            when (result) {
                is Resource.Success -> {
                    Log.e("MyTag", "Resource.Success ${result.data!!}")
                    when (order) {
                        1 -> _miVehicle.value = result.data!!
                        2 -> _vehicleRival.value = result.data!!
                    }

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
}