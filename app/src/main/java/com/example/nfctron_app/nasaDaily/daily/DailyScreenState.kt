package com.example.nfctron_app.nasaDaily.daily

import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDaily

sealed class DailyScreenState {
    data class Success(val data: NasaDaily) : DailyScreenState()
    object Error : DailyScreenState()
    object Loading : DailyScreenState()
}