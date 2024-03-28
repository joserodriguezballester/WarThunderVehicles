package com.example.warthundervehicles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.warthundervehicles.data.local.entities.MachineEntity
import androidx.room.Query

@Dao
interface MachineDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserMachine (machine: MachineEntity)

    @Query("SELECT * FROM Machines")
    suspend fun getMachines(): List<MachineEntity>
}