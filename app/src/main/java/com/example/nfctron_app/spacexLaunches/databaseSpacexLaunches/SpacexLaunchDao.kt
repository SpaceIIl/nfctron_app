package com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem

@Dao
interface SpacexLaunchDao {
    @Insert
    suspend fun insertSpacexLaunch(SpacexLaunch: SpacexLaunch)

    @Query("DELETE FROM launch")
    suspend fun deleteAllSpacexLaunch()

    @Query("DELETE FROM launch WHERE launchId = :launchId")
    suspend fun deleteSpacexLaunch(launchId: String)

    @Query("SELECT * FROM launch WHERE launchId = :launchId")
    suspend fun getSpacexLaunchById(launchId: String): SpacexLaunch?

    @Query("SELECT * FROM launch")
    suspend fun getAllSpacexLaunch(): List<SpacexLaunch>
}