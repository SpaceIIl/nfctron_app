package com.example.nfctron_app.nasaDaily.databaseNasaDaily

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NasaDaily::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nasaDailyDao(): NasaDailyDao
}