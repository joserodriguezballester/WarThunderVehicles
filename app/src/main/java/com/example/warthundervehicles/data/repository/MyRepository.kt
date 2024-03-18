package com.example.warthundervehicles.data.repository


import NewRemoteVehicle
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicle
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem

import com.example.warthundervehicles.utils.Resource
import retrofit2.Response


interface MyRepository {
   // suspend fun getVehicle(identifier: String): Response<RemoteVehicleListItem>
    suspend fun getVehicle(identifier: String): Resource<NewRemoteVehicle>
    suspend fun getVehiclePrueba(identifier: String):Any
    suspend fun getAllVehicles(limit: Int): Response<List<RemoteVehicleListItem>>


    suspend fun getAllVehiclesRemoteCountryRank(limit: Int, country: String, rank: Int):
            Resource<List<RemoteVehicleListItem>>


    suspend fun getVehicles(limit: Int): Resource<List<RemoteVehicleListItem>>

    //   suspend fun getAllVehiclesCountryRank(limit: Int,country:String, rank: Int): Response<List<SinnombreItem>>
    suspend fun getVehiclesList(limit: Int): Resource<List<RemoteVehicleListItem>>

    //   suspend fun getAllVehiclesRank(limit: Int, rank: Int): Response<List<RemoteVehicleListItem>>
    suspend fun getAllVehiclesRank(limit: Int, era: Int): Resource<List<RemoteVehicleListItem>>

    //  suspend fun getAllVehiclesCountryRank(limit: Int, country: String, rank: Int): Response<List<RemoteVehicleListItem>>
    suspend fun getLimitVehiclesCountryRank(limit: Int, country: String, rank: Int):
            Resource<List<RemoteVehicleListItem>>

//    suspend fun getAllVehiclesCountryRank( country: String, rank: Int):
//            Resource<List<RemoteVehicleListItem>>
    suspend fun getAllVehiclesCountry(country: String): Resource<List<RemoteVehicleListItem>>

    suspend fun getVehiclesArmyCountryRank(army:String,country:String,rank:Int):Resource<List<RemoteVehicleListItem>>
}