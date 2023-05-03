package com.example.nfctron_app.spacexLaunches.spacexDataSource

import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem
import com.example.nfctron_app.spacexLaunches.spacexService.SpacexService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SpacexDataSource {
    private val spacexService by lazy {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(SpacexService::class.java)
    }

    suspend fun getLaunches(): Response<List<LaunchesItem>> {
        return spacexService.getLaunches()
    }
}