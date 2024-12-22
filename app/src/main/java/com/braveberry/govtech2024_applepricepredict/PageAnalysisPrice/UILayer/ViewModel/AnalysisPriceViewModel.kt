package com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Domain.HomeDomain
import com.braveberry.govtech2024_applepricepredict.R
import kotlin.math.absoluteValue

class AnalysisPriceViewModel: ViewModel() {
    val homeDomain = HomeDomain()
    private val _uiState = MutableLiveData<AnalysisPriceFragmentStatus>()
    val uiState: LiveData<AnalysisPriceFragmentStatus> get() = _uiState
    init {
        val analysisPriceFragmentStatus = AnalysisPriceFragmentStatus(
            selectedPlant = "",
            currentPrice = 0,
            priceGapString = "",
            imagePath = R.drawable.apple_image
        )
        _uiState.value = analysisPriceFragmentStatus
    }

    /**
     * 작물 이름 받으면 바로 나머지 데이터 갱신
     */
    fun getPlantName(plantName: String){
        _uiState.value = _uiState.value?.copy(selectedPlant = plantName)
        setCurrentPrice(plantName)
        getPriceGap(plantName)
        getPlantImage(plantName)
    }
    /**
     * 현재 가격 세팅
     */
    private fun setCurrentPrice(plantName: String){
        val priceList = homeDomain.getCurrentPriceList()
        for(price in priceList){
            if(price.plantName == plantName){
                _uiState.value = _uiState.value?.copy(currentPrice = price.currentPrice)
            }
        }
    }
    /**
     * 가격 차이 받아오기
     */
    private fun getPriceGap(plantName: String){
        val gapList = homeDomain.getPlantPriceGap()
        for (i in gapList.indices){
            if (gapList.get(i).plantName == plantName){
                val priceGap = gapList.get(i).gap
                val priceGapString: String
                if ( priceGap < 0){
                    priceGapString = "어제보다 " +  priceGap.absoluteValue.toString() + "원 하락 ▼"
                }
                else if (priceGap > 0){
                    priceGapString = "어제보다 " + priceGap.toString() + "원 상승 ▲"
                }
                else{
                    priceGapString = "가격 변동 없음"
                }
                _uiState.value = _uiState.value?.copy(priceGapString = priceGapString)
            }
        }
    }
    /**
     * 작물 이미지 세팅
     */
    private fun getPlantImage(plantName: String){
        when(plantName){
            "사과" -> _uiState.value = _uiState.value?.copy(imagePath = R.drawable.apple_image)
            "감자" -> _uiState.value = _uiState.value?.copy(imagePath = R.drawable.potato_image)
            "고추" -> _uiState.value = _uiState.value?.copy(imagePath = R.drawable.pepper_image)
        }
    }
}

data class AnalysisPriceFragmentStatus(
    val selectedPlant: String,
    val currentPrice: Int,
    val priceGapString: String,
    val imagePath: Int
)