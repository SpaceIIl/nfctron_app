package com.example.nfctron_app.daily

import com.example.nfctron_app.database.NasaDaily

sealed class DailyScreenState {
    data class Success(val data: NasaDaily) : DailyScreenState()
    data class Error(val throwable: Throwable) : DailyScreenState()
    object Loading : DailyScreenState()
}