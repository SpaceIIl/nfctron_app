package com.example.nfctron_app.spacexLaunches.spacexService

import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDaily
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SpacexService {
    @GET("v4/launches")
    suspend fun getLaunches(): Response<List<LaunchesItem>>
}