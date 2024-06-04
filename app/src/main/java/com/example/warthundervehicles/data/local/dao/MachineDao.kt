package com.example.warthundervehicles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.warthundervehicles.data.local.entities.MachineEntity
import androidx.room.Query
import com.example.warthundervehicles.data.remote.apimodels.version2.RemoteVehicleListItem
import com.example.warthundervehicles.modelsApp.Machine
import com.example.warthundervehicles.utils.Resource

@Dao
interface MachineDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserMachine (machine: MachineEntity)

    @Query("SELECT * FROM Machines")
    suspend fun getMachines(): List<MachineEntity>

    @Query("SELECT * FROM Machines WHERE country = :country AND era = :rank ")
    suspend fun getMachinesCountryRank(country: String, rank: Int):List<MachineEntity>


}