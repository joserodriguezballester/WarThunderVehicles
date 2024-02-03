package com.example.warthundervehicles.di

import com.example.warthundervehicles.data.remote.MyApi
import com.example.warthundervehicles.data.repository.MyRepository
import com.example.warthundervehicles.data.repository.MyRepositoryImpl
import com.example.warthundervehicles.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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