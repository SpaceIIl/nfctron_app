package com.example.nfctron_app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDaily
import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDailyDao
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunch
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunchDao

@Database(entities = [NasaDaily::class, SpacexLaunch::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nasaDailyDao(): NasaDailyDao
    abstract fun spacexLaunchDao(): SpacexLaunchDao
}