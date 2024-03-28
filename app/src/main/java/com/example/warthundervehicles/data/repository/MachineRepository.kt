package com.example.warthundervehicles.data.repository

import com.example.warthundervehicles.data.local.dao.MachineDao
import com.example.warthundervehicles.data.local.entities.MachineEntity
import com.example.warthundervehicles.modelsApp.Machine
import javax.inject.Inject

class MachineRepository @Inject constructor(
    private val machineDao: MachineDao
) {
    suspend fun getMachines(): List<Machine> {
        val entities = machineDao.getMachines()
        return entities.map {
            Machine(name = it.name, identifier = it.identifier, imagen = it.imagen)
        }
    }

    suspend fun insertMachine(machine: Machine) {
        val entity = MachineEntity(
            name = machine.name,
            identifier = machine.identifier,
            imagen = machine.imagen
        )
        machineDao.inserMachine(entity)
    }
}