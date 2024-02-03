package com.example.warthundervehicles.data.repository

import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.data.remote.apimodels.Vehicles
import com.example.warthundervehicles.utils.Resource
import retrofit2.Response


interface MyRepository {
    suspend fun getVehicle(identifier: String):  Response<RemoteVehiclesItem>
    suspend fun getAllVehicles(limit: Int): Response<List<RemoteVehiclesItem>>
    suspend fun getAllVehiclesRank(limit: Int, rank: Int): Response<List<RemoteVehiclesItem>>
    suspend fun getAllVehiclesCountryRank(
        limit: Int,
        country: String,
        rank: Int
    ): Response<List<RemoteVehiclesItem>>
    suspend fun getAllVehiclesCountry(
        limit: Int,
        country: String,
    ): Response<List<RemoteVehiclesItem>>
    //   suspend fun getAllVehiclesCountryRank(limit: Int,country:String, rank: Int): Response<List<SinnombreItem>>

}