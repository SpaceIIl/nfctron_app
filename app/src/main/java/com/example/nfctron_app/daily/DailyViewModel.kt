package com.example.nfctron_app.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfctron_app.model.pvod
import kotlinx.coroutines.launch
import com.example.nfctron_app.nasa.NasaDataSource.getNasaDaily

class DailyViewModel : ViewModel() {
    private val _screenState = MutableLiveData<DailyScreenState>()
    val screenState: LiveData<DailyScreenState> = _screenState

    init {
        loadData()
    }

    private fun loadData() {
        _screenState.value = DailyScreenState.Loading

        viewModelScope.launch {
            try {
                val daily = getNasaDaily().body()!!
                _screenState.postValue(DailyScreenState.Success(daily))
            }catch (exception: Exception){
                _screenState.postValue(DailyScreenState.Error(Throwable()))
            }
        }
    }

    fun retryLoadingData() {
        loadData()
    }
}