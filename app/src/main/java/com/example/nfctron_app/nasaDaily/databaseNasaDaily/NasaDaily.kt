package com.example.nfctron_app.nasaDaily.databaseNasaDaily

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class NasaDaily(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val title: String,
    val url: String
)

