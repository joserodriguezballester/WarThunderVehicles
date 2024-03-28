package com.example.warthundervehicles.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "machines")
data class MachineEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val name:String,
    val identifier:String,
    val imagen:String,
)
