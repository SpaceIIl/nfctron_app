package com.example.nfctron_app.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NasaDaily::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nasaDailyDao(): NasaDailyDao
}