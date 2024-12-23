package com.braveberry.govtech2024_applepricepredict.PageAnalysis.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Domain.AnalysisDomain
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisCurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisPlantDataModel
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeFragmentStatus
import com.github.mikephil.charting.data.Entry


class AnalysisViewModel: ViewModel() {
    // UI 상태 mutable
    private val _uiState = MutableLiveData<AnalysisFragmentStatus>()
    // UI 상태 체크
    val uiState: LiveData<AnalysisFragmentStatus> get() = _uiState
    // 도메인 레이어
    private val analysisDomain = AnalysisDomain()

    init {
        // 초기 UI 상태 데이터 클래스 생성
        val analysisFragmentStatus = AnalysisFragmentStatus(
            selectedPlant = "",
            recyclerViewItemList = analysisDomain.getPlantDataList(),
            plantEntryList = emptyList(),
            currentPriceList = setCurrentPrice()
        )
        // UI 상태 초기화
        _uiState.value = analysisFragmentStatus
    }


    /**
     * 작물 데이터 클래스에 세팅
     */
    fun setSelectedPlant(plant: String) {
        _uiState.value = _uiState.value?.copy(selectedPlant = plant)
        // 이름 세팅과 동시에 차트 데이터도 세팅
        getPlantEntryList(plant)
    }


    /**
     * 차트 데이터 데이터 레이어에서 뽑아오기
     */
    fun getPlantEntryList(plant: String) {
        _uiState.value = _uiState.value?.copy(plantEntryList = analysisDomain.getPlantEntryList(plant))
    }

    ////그냥 양 데이터

    /**
     * 현재 가격 받아오기
     */
    private fun setCurrentPrice(): List<AnalysisCurrentPriceModel> {
        return analysisDomain.getCurrentPriceList()
    }


    /**
     * 가격 하락으로 텍스트와 색상 변경
     */
    fun setPriceDrop() {
        _uiState.value = _uiState.value?.copy(
            priceText = "전년 대비 가격 하락",
            cardColor = "#2196F3" // 파란색으로 변경
        )
    }
}

data class AnalysisFragmentStatus(
    val selectedPlant: String,
    val recyclerViewItemList: List<AnalysisPlantDataModel>,
    //얘는 차트 위한
    val plantEntryList: List<Entry>,
    val currentPriceList: List<AnalysisCurrentPriceModel>,
    val priceText: String = "전년 대비 가격 상승", // 기본값은 가격 상승
    val cardColor: String = "#D43A3A" // 기본값은 빨간색
)
