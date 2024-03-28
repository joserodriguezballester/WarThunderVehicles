package com.example.warthundervehicles.di

import android.content.Context
import androidx.room.Room
import com.example.warthundervehicles.data.local.dao.MachineDao
import com.example.warthundervehicles.data.local.database.MachineDatabase
import com.example.warthundervehicles.data.repository.MachineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    @Provides
    fun provideMachineDatabase(@ApplicationContext context: Context): MachineDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MachineDatabase::class.java,
            "machine_database"
        ).build()
    }

    @Provides
    fun provideMachineDao (machineDatabase: MachineDatabase): MachineDao {
        return machineDatabase.machineDao
    }
}