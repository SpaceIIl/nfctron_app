package com.example.nfctron_app.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nfctron_app.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NasaDailyRepository {
    private val database: AppDatabase = Room.databaseBuilder(
        App.instance.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Set default value for symbol field
            db.execSQL("INSERT INTO date (date) VALUES ('BTCUSDT'), ('ETHUSDT'), ('BNBUSDT')")
        }
    }).build()

    private val nasaDailyDao = database.nasaDailyDao()

    suspend fun insertNasaDaily(
        date: String,
        explanation: String,
        hdurl: String,
        mediaType: String,
        serviceVersion: String,
        title: String,
        url: String
    ) {
        withContext(Dispatchers.IO) {
            val nasaDaily = NasaDaily(
                date = date,
                explanation = explanation,
                hdurl = hdurl,
                media_type = mediaType,
                service_version = serviceVersion,
                title = title,
                url = url
            )
            nasaDailyDao.insertNasaDaily(nasaDaily)
        }
    }

    suspend fun deleteNasaDaily(date: String) {
        withContext(Dispatchers.IO){
            nasaDailyDao.deleteNasaDaily(date)
        }
    }
}