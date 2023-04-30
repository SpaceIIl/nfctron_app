package com.example.nfctron_app.spacexLaunches.launches

import com.example.nfctron_app.nasaDaily.daily.DailyScreenState
import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDaily
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem

sealed class LaunchesScreenState {
    data class Success(val data: List<LaunchesItem>) : LaunchesScreenState()
    object Error : LaunchesScreenState()
    object Loading : LaunchesScreenState()
}