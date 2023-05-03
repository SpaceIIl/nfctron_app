package com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SpacexLaunchDao {
    @Insert
    suspend fun insertSpacexLaunch(string: SpacexLaunch)

    @Query("DELETE FROM launch")
    suspend fun deleteAllSpacexLaunch()

    @Query("DELETE FROM launch WHERE id = :launchId")
    suspend fun deleteSpacexLaunch(launchId: String)

    @Query("SELECT * FROM launch")
    suspend fun getAllSpacexLaunch(): List<SpacexLaunch>
}