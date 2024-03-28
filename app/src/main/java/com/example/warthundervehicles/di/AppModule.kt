package com.example.warthundervehicles.di

import android.content.Context
import androidx.room.Room
import com.example.warthundervehicles.data.local.dao.MachineDao
import com.example.warthundervehicles.data.local.database.MachineDatabase
import com.example.warthundervehicles.data.remote.api.MyApi
import com.example.warthundervehicles.data.repository.MyRepository
import com.example.warthundervehicles.data.repository.MyRepositoryImpl
//import com.example.warthundervehicles.ui.screens.MyViewModel
//import com.example.warthundervehicles.ui.screens.MyViewModelFactory
import com.example.warthundervehicles.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Provides
//    fun provideMyViewModelFactory(
//        repository: MyRepository
//    ): MyViewModelFactory {
//        return object : MyViewModelFactory {
//            override fun create(machineDao: MachineDao): MyViewModel {
//                return MyViewModel(repository, machineDao)
//            }
//        }
//    }


    @Provides
    @Singleton
    fun provideMyApi(): MyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(MyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMyRepository(MyApi: MyApi): MyRepository {
        //  val apiService= RetrofitApiFactory.provideRetrofitApi()
        return MyRepositoryImpl(MyApi)

    }

}