package com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch")
data class SpacexLaunch(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val webcast: String,
    val wikipedia: String,
    val name: String,
    val dateLocal: String,
    val launchId: String
)