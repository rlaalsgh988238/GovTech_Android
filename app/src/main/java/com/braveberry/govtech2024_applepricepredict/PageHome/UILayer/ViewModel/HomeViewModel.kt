package com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Domain.AnalysisDomain
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisCurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisPlantDataModel
import com.github.mikephil.charting.data.Entry

/**
 * 홈 화면 뷰모델
 */
class HomeViewModel: ViewModel() {
    // UI 상태 mutable
    private val _uiState = MutableLiveData<HomeFragmentStatus>()
    // UI 상태 체크
    val uiState: LiveData<HomeFragmentStatus> get() = _uiState
    // 도메인 레이어
    private val homeDomain = AnalysisDomain()
    /**
     * 뷰모델 초기화
     */
    init {
        // 초기 UI 상태 데이터 클래스 생성
        val homeFragmentStatus = HomeFragmentStatus(
            selectedPlant = "",
            recyclerViewItemList = homeDomain.getPlantDataList(),
            plantEntryList = emptyList(),
            currentPriceList = setCurrentPrice()
        )
        // UI 상태 초기화
        _uiState.value = homeFragmentStatus
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
    fun getPlantEntryList(plant: String){
        _uiState.value = _uiState.value?.copy(plantEntryList = homeDomain.getPlantEntryList(plant))
    }
    /**
     * 현재 가격 받아오기
     */
    private fun setCurrentPrice():List<AnalysisCurrentPriceModel>{
        return homeDomain.getCurrentPriceList()
    }
}

/**
 * 홈 화면 프래그먼트 UI 상태 홀딩
 */
data class HomeFragmentStatus(
    val selectedPlant: String,
    val recyclerViewItemList: List<AnalysisPlantDataModel>,
    val plantEntryList: List<Entry>,
    val currentPriceList: List<AnalysisCurrentPriceModel>
)