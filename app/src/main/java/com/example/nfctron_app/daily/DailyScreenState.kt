package com.example.nfctron_app.daily

import com.example.nfctron_app.database.NasaDaily

sealed class DailyScreenState {
    data class Success(val data: NasaDaily) : DailyScreenState()
    object Error : DailyScreenState()
    object Loading : DailyScreenState()
}