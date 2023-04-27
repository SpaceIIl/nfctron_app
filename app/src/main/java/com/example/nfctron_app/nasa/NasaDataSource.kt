package com.example.nfctron_app.nasa

import com.example.nfctron_app.ApiService
import com.example.nfctron_app.database.NasaDaily
import com.example.nfctron_app.model.pvod
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NasaDataSource {
    private val NasaService by lazy {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(ApiService::class.java)
    }

    suspend fun getNasaDaily(): Response<NasaDaily> {
        return NasaService.getNasaDaily()
    }
}
