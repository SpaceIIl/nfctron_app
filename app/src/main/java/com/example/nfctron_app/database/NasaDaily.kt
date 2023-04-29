package com.example.nfctron_app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class NasaDaily(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    //@ColumnInfo(name = "date")
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String?,
    val url: String?
)

