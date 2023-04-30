package com.example.nfctron_app.nasaDaily.databaseNasaDaily

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NasaDailyDao {
    @Insert
    suspend fun insertNasaDaily(string: NasaDaily)

    @Query("DELETE FROM daily")
    suspend fun deleteNasaDaily()

    @Query("SELECT * FROM daily ORDER BY id DESC LIMIT 1")
    suspend fun getNasaDaily(): NasaDaily
}