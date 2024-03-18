package com.example.warthundervehicles.data.repository


import NewRemoteVehicle
import android.util.Log
import com.example.warthundervehicles.data.remote.MyApi
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicle

import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem

import com.example.warthundervehicles.utils.Resource
import retrofit2.Response


class MyRepositoryImpl(private val myApi: MyApi) : MyRepository {


    override suspend fun getVehicle(identifier: String): Resource<NewRemoteVehicle> {
        val response = try {
            Log.i("MyTag","identifier $identifier")
            myApi.getVehicle(identifier)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
    override suspend fun getVehiclePrueba(identifier: String):Any {
        val response = try {
            Log.i("MyTag","identifier:: $identifier")
            myApi.getVehiclePrueba(identifier)
            Log.i("MyTag","identifier- ${myApi.getVehiclePrueba(identifier)}")
        } catch (e: Exception) {
            return  e.printStackTrace()
        }
        Log.i("MyTag","response:: $response")
        return response
    }
//    override suspend fun getVehicle(identifier: String): Response<RemoteVehicleListItem> {
//        return myApi.getVehicle(identifier)
//    }

    override suspend fun getAllVehicles(limit: Int): Response<List<RemoteVehicleListItem>> {
        return myApi.getAllVehicles()
    }


    override suspend fun getVehicles(
        limit: Int,
    ): Resource<List<RemoteVehicleListItem>> {
        return myApi.getVehiclesRemote(limit)
    }

//    override suspend fun getAllVehiclesCountry(
//        country: String,
//    ): Resource<List<RemoteVehicleListItem>> {
//        val response=try{
//            myApi.getAllVehiclesCountry( country)
//        }catch (e:Exception){
//            return Resource.Error("getAllVehiclesCountry An unknown error occured.")
//        }
//        return Resource.Success(response)
//    }

    override suspend fun getVehiclesList(limit: Int): Resource<List<RemoteVehicleListItem>> {
        val response = try {
            myApi.getVehiclesList(limit)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getAllVehiclesRank(
        limit: Int,
        era: Int
    ): Resource<List<RemoteVehicleListItem>> {
        val response = try {
            myApi.getAllVehiclesRank(limit, era)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getLimitVehiclesCountryRank(
        limit: Int,
        country: String,
        rank: Int
    ): Resource<List<RemoteVehicleListItem>> {
        val response = try {
            myApi.getLimitVehiclesCountryRank(limit, country, rank)
        } catch (e: Exception) {
            return Resource.Error("getAllVehiclesCountryRank An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getAllVehiclesCountry(
        country: String,
    ): Resource<List<RemoteVehicleListItem>> {
        val response = try {
            myApi.getAllVehiclesCountry(1000, country)
        } catch (e: Exception) {
            return Resource.Error("getAllVehiclesCountryRank An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getVehiclesArmyCountryRank(
        army: String,
        country: String,
        rank: Int
    ): Resource<List<RemoteVehicleListItem>> {
        val response = try {
            myApi.getVehiclesArmyCountryRank(army, country, rank)
        } catch (e: Exception) {
            return Resource.Error("getVehiclesArmyCountryRank An unknown error occured.")
        }
        return Resource.Success(response)

    }


    override suspend fun getAllVehiclesRemoteCountryRank(
        limit: Int,
        country: String,
        rank: Int
    ): Resource<List<RemoteVehicleListItem>> {
        val response = try {
            myApi.getAllVehiclesCountryRank(country, rank)

        } catch (e: Exception) {
            return Resource.Error("getAllVehiclesCountryRank An unknown error occured.")
        }
        return Resource.Success(response)

    }

}

