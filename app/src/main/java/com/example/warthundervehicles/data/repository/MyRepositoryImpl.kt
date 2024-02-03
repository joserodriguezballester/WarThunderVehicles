package com.example.warthundervehicles.data.repository


import com.example.warthundervehicles.data.remote.MyApi
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.data.remote.apimodels.Vehicles
import com.example.warthundervehicles.utils.Resource
import retrofit2.Response


class MyRepositoryImpl(private val myApi: MyApi) : MyRepository {


    override suspend fun getVehicle(identifier: String):Response<RemoteVehiclesItem>  {
        return myApi.getVehicle(identifier)
    }

    override suspend fun getAllVehicles(limit: Int): Response<List<RemoteVehiclesItem>> {
        return myApi.getAllVehicles()
    }

    override suspend fun getAllVehiclesRank(
        limit: Int,
        rank: Int
    ): Response<List<RemoteVehiclesItem>> {
        return myApi.getAllVehiclesRank(limit, rank)
    }

    override suspend fun getAllVehiclesCountryRank(
        limit: Int,
        country: String,
        rank: Int
    ): Response<List<RemoteVehiclesItem>> {
        return myApi.getAllVehiclesCountryRank(limit,country,rank)
    }
    override suspend fun getAllVehiclesCountry(
        limit: Int,
        country: String,
    ): Response<List<RemoteVehiclesItem>> {
        return myApi.getAllVehiclesCountry(limit,country)
    }
}