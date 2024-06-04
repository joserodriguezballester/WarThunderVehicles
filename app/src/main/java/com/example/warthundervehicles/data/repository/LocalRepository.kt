package com.example.warthundervehicles.data.repository

import com.example.warthundervehicles.data.local.dao.MachineDao
import com.example.warthundervehicles.data.local.entities.MachineEntity
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.utils.Resource
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val machineDao: MachineDao
) {
    suspend fun getMachines(): List<Machine> {
        val entities = machineDao.getMachines()
        return entities.map {
            Machine(
                name = it.name,
                identifier = it.identifier,
                imagen = it.imagen,
                country = it.country,
                rank = it.era,
                arcade_br = it.arcade_br,
                vehicle_type = it.vehicle_type,
            )
        }
    }

    suspend fun insertMachine(machine: Machine) {
        val entity = MachineEntity(
            name = machine.name,
            identifier = machine.identifier,
            imagen = machine.imagen,
            country = machine.country,
            era = machine.rank,
            arcade_br = machine.arcade_br,
            vehicle_type = machine.vehicle_type,
            )
        machineDao.inserMachine(entity)
    }

    suspend fun getVehiclesLocalCountryRank(
        country: String,
        rank: Int
    ): Resource<List<MachineEntity>> {

        return try {
            val machines = machineDao.getMachinesCountryRank(country, rank)
            Resource.Success(machines)
        } catch (e: Exception) {
            Resource.Error("Error al obtener las máquinas del país y rango especificados: ${e.message}")
        }


    }
}