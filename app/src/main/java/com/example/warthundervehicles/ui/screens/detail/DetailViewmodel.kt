package com.example.warthundervehicles.ui.screens.detail

import NewRemoteVehicle
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicle
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem

import com.example.warthundervehicles.data.repository.MyRepository
import com.example.warthundervehicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class DetailViewmodel @Inject constructor(private val repository: MyRepository) : ViewModel() {
    private val _selectedVehicle = mutableStateOf<NewRemoteVehicle?>(null)
    val selectedVehicle: MutableState<NewRemoteVehicle?> get() = _selectedVehicle

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

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
    suspend fun getVehicle(identifier: String): Resource<NewRemoteVehicle> {
        return try {
            val result: Resource<NewRemoteVehicle> = repository.getVehicle(identifier)
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

}