package com.example.warthundervehicles.data.remote

//import com.example.warthundervehicles.data.remote.borrar.VehicleRemote

import com.example.warthundervehicles.data.remote.apimodels.Vehicles
import com.example.warthundervehicles.data.remote.apimodels.version2.VehicleListItem
import com.example.warthundervehicles.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

///const val BASE_URL = "http://wtvehiclesapi.sgambe.serv00.net/"

interface MyApi {
    @GET("api/vehicles/{identifier}")
    suspend fun getVehicle(
        @Path("identifier") identifier: String
    ):Response<VehicleListItem>

    //   @Query("limit") limit: Int = 1000
    @GET("api/vehicles/")
    suspend fun getAllVehicles(@Query("limit") limit: Int = 1000): Response<List<VehicleListItem>>


    // "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&rank=1"
    @GET("api/vehicles")
    suspend fun getAllVehiclesRank(
        @Query("limit") limit: Int = 1000,
        @Query("rank") rank: Int = 1
    ): Response<List<VehicleListItem>>

    //  "http://wtvehiclesapi.sgambe.serv00.net/api/vehicles?limit=1000&country=ussr&rank=1"
    @GET("api/vehicles")
    suspend fun getAllVehiclesCountryRank(
        @Query("limit") limit: Int = 1000,
        @Query("country") country:String,
        @Query("rank") rank: Int
    ): Response<List<VehicleListItem>>

    @GET("api/vehicles")
    suspend fun getAllVehiclesCountry(
        @Query("limit") limit: Int = 1000,
        @Query("country") country:String,

        ): Response<List<VehicleListItem>>
}