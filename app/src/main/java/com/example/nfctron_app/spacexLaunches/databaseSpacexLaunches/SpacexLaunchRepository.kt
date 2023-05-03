package com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches

import androidx.room.Room
import com.example.nfctron_app.App
import com.example.nfctron_app.AppDatabase
import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDaily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SpacexLaunchRepository {
    private val database: AppDatabase = Room.databaseBuilder(
        App.instance.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    private val spacexLaunchDao = database.spacexLaunchDao()

    suspend fun insertSpacexLaunch(
        webcast: String,
        wikipedia: String,
        name: String,
        dateLocal: String,
        launchId: String
    ) {
        withContext(Dispatchers.IO) {
            val spacexLaunch = SpacexLaunch(
                webcast = webcast,
                wikipedia = wikipedia,
                name = name,
                dateLocal = dateLocal,
                launchId = launchId
            )
            spacexLaunchDao.insertSpacexLaunch(spacexLaunch)
        }
    }

    suspend fun deleteAllSpacexLaunch() {
        withContext(Dispatchers.IO){
            spacexLaunchDao.deleteAllSpacexLaunch()
        }
    }

    suspend fun deleteSpacexLaunch(launchId: String) {
        return withContext(Dispatchers.IO) {
            spacexLaunchDao.deleteSpacexLaunch(launchId)
        }
    }

    suspend fun getAllSpacexLaunch(): List<SpacexLaunch> {
        return withContext(Dispatchers.IO) {
            spacexLaunchDao.getAllSpacexLaunch()
        }
    }
}