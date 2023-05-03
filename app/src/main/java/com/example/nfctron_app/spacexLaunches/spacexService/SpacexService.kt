package com.example.nfctron_app.spacexLaunches.spacexService

import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem
import retrofit2.Response
import retrofit2.http.GET

interface SpacexService {
    @GET("v4/launches")
    suspend fun getLaunches(): Response<List<LaunchesItem>>
}