package com.braveberry.govtech2024_applepricepredict.PageHome.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _uiState = MutableLiveData<HomeFragmentStatus>()
    val uiState: LiveData<HomeFragmentStatus> get() = _uiState
}

data class HomeFragmentStatus(
    val status: String
)