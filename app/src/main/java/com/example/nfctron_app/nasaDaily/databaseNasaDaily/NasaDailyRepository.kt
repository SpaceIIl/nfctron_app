package com.example.nfctron_app.nasaDaily.databaseNasaDaily

import androidx.room.Room
import com.example.nfctron_app.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NasaDailyRepository {
    private val database: AppDatabase = Room.databaseBuilder(
        App.instance.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    private val nasaDailyDao = database.nasaDailyDao()

    suspend fun insertNasaDaily(
        date: String,
        explanation: String,
        hdurl: String,
        title: String,
        url: String
    ) {
        withContext(Dispatchers.IO) {
            val nasaDaily = NasaDaily(
                date = date,
                explanation = explanation,
                hdurl = hdurl,
                title = title,
                url = url
            )
            nasaDailyDao.insertNasaDaily(nasaDaily)
        }
    }

    suspend fun deleteNasaDaily() {
        withContext(Dispatchers.IO){
            nasaDailyDao.deleteNasaDaily()
        }
    }

    suspend fun getNasaDaily(): NasaDaily {
        return withContext(Dispatchers.IO) {
            nasaDailyDao.getNasaDaily()
        }
    }

}