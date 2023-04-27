package com.example.nfctron_app

import com.example.nfctron_app.database.NasaDaily
import com.example.nfctron_app.model.pvod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("planetary/apod")
    suspend fun getNasaDaily(
        @Query("api_key") apiKey: String = "6kaTZZ2Gl6ddPfTqH2gKLs05yvMrHiLEn8pTEYn0"
    ): Response<NasaDaily>
}