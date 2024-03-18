package com.example.warthundervehicles.data.remote


import NewRemoteVehicle
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicle
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem

import com.example.warthundervehicles.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

///const val BASE_URL = "http://wtvehiclesapi.sgambe.serv00.net/"
////  OJO CAMBIO DE rank POR era
// ANTIGUA "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&rank=1"
// ACTUAL 'http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=50&era=1'
// 'http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?country=germany'

interface MyApi {

    @GET("api/vehicles/{identifier}")
    suspend fun getVehicle(
        @Path("identifier") identifier: String
    ): NewRemoteVehicle
    @GET("api/vehicles/{identifier}")
    suspend fun getVehiclePrueba(
        @Path("identifier") identifier: String
    ): Any // Cambia el tipo de retorno a lo que necesites

    @GET("api/vehicles/")
    suspend fun getAllVehicles(@Query("limit") limit: Int = 1000): Response<List<RemoteVehicleListItem>>


//    @GET("api/vehicles")
//    suspend fun getAllVehiclesRemoteCountryRank(
//        @Query("limit") limit: Int = 1000,
//        @Query("country") country:String,
//        @Query("rank") rank: Int
//    ): Resource<List<RemoteVehicleListItem>>

    @GET("api/vehicles/")
    suspend fun getVehiclesRemote(@Query("limit") limit: Int ): Resource<List<RemoteVehicleListItem>>

    //////// BUENA
    @GET("api/vehicles/")
    suspend fun getVehiclesList(@Query("limit") limit: Int ): List<RemoteVehicleListItem>

    // ANTIGUA "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&rank=1"
   // 'http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=50&era=1'
    @GET("api/vehicles")
    suspend fun getAllVehiclesRank(
        @Query("limit") limit: Int = 1000,
        @Query("era") era: Int = 1
    ): List<RemoteVehicleListItem>

    //  "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&country=ussr&rank=1"
    @GET("api/vehicles")
    suspend fun getLimitVehiclesCountryRank(
        @Query("limit") limit: Int = 1000,
        @Query("country") country:String,
        @Query("era") rank: Int
    ): List<RemoteVehicleListItem>
    //  "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&country=ussr&rank=1"
    @GET("api/vehicles")
    suspend fun getAllVehiclesCountryRank(
        @Query("country") country:String,
        @Query("era") rank: Int
    ): List<RemoteVehicleListItem>

    @GET("api/vehicles")
    suspend fun getAllVehiclesCountry(
        @Query("limit") limit: Int = 1000,
        @Query("country") country:String,
    ): List<RemoteVehicleListItem>

    //  "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&country=ussr&rank=1"
   //   'http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=50&country=france&type=lighttank&era=1' \
    @GET("api/vehicles")
    suspend fun getVehiclesArmyCountryRank(
        @Query("type")type:String,
        @Query("country") country:String,
        @Query("era") rank: Int
    ): List<RemoteVehicleListItem>

}