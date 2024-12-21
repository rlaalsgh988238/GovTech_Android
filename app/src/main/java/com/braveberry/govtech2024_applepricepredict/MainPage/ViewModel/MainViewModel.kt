package com.braveberry.govtech2024_applepricepredict.MainPage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _uiState = MutableLiveData<UiStatus>()
    val uiState: LiveData<UiStatus> get() = _uiState

    init {
        _uiState.value = UiStatus()
    }

    fun setNavigationStatus(status: String) {
        _uiState.value = _uiState.value?.copy(navigationStatus = status)
        Log.d("test log", "setNavigationStatus: $status")
    }
}

data class UiStatus(
    val navigationStatus: String = "HomeFragment"
)