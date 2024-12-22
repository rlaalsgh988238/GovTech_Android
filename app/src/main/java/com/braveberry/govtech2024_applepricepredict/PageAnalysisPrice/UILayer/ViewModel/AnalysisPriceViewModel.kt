package com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnalysisPriceViewModel: ViewModel() {
    private val _uiState = MutableLiveData<AnalysisPriceFragmentStatus>()
    val uiState: LiveData<AnalysisPriceFragmentStatus> get() = _uiState
    init {
        val analysisPriceFragmentStatus = AnalysisPriceFragmentStatus(
            selectedPlant = "",
            currentPrice = 0,
            priceGapString = ""
        )
    }
}

data class AnalysisPriceFragmentStatus(
    val selectedPlant: String,
    val currentPrice: Int,
    val priceGapString: String
)