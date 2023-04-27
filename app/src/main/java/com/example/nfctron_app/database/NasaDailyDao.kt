package com.example.nfctron_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NasaDailyDao {
    @Insert
    suspend fun insertNasaDaily(string: NasaDaily)

    @Query("DELETE FROM daily WHERE date=:date")
    suspend fun deleteNasaDaily(date: String)
}