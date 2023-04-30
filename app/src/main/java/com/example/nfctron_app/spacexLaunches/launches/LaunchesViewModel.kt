package com.example.nfctron_app.spacexLaunches.launches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfctron_app.nasaDaily.daily.DailyScreenState
import kotlinx.coroutines.launch
import com.example.nfctron_app.nasaDaily.nasaDataSource.NasaDataSource.getNasaDaily
import com.example.nfctron_app.spacexLaunches.spacexDataSource.SpacexDataSource.getLaunches

class LaunchesViewModel : ViewModel() {
    private val _screenState = MutableLiveData<LaunchesScreenState>()
    val screenState: LiveData<LaunchesScreenState> = _screenState

    init {
        loadData()
    }

    private fun loadData() {
        _screenState.value = LaunchesScreenState.Loading

        viewModelScope.launch {
            try {
                val launches = getLaunches().body()!!
                _screenState.postValue(LaunchesScreenState.Success(launches))
            }catch (exception: Exception){
                _screenState.postValue(LaunchesScreenState.Error)
            }
        }
    }


    fun retryLoadingData() {
        loadData()
    }
}