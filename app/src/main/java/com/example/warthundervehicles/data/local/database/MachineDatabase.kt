package com.example.warthundervehicles.data.local.database

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.warthundervehicles.data.local.dao.MachineDao
import com.example.warthundervehicles.data.local.entities.MachineEntity
import com.example.warthundervehicles.modelsApp.Machine
import javax.inject.Inject
import javax.inject.Singleton
//@Database(entities = { Machine.class}, version = 1)
@Database(entities = [MachineEntity::class],version=1)
abstract class MachineDatabase:RoomDatabase(){
    abstract val machineDao:MachineDao
}
