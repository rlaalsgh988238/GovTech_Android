package com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Domain.HomeDomain
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.CurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.PlantDataModel
import com.github.mikephil.charting.data.Entry
import kotlin.math.absoluteValue

/**
 * 홈 화면 뷰모델
 */
class HomeViewModel: ViewModel() {
    // UI 상태 mutable
    private val _uiState = MutableLiveData<HomeFragmentStatus>()
    // UI 상태 체크
    val uiState: LiveData<HomeFragmentStatus> get() = _uiState
    // 도메인 레이어
    private val homeDomain = HomeDomain()
    /**
     * 뷰모델 초기화
     */
    init {
        // 초기 UI 상태 데이터 클래스 생성
        val homeFragmentStatus = HomeFragmentStatus(
            selectedPlant = "",
            recyclerViewItemList = homeDomain.getPlantDataList(),
            plantEntryList = emptyList(),
            currentPriceList = setCurrentPrice(),
            priceGapString = "가격이 어떻게 달라졌을까요?"
        )
        // UI 상태 초기화
        _uiState.value = homeFragmentStatus
    }
    /**
     * 선택된 작물 세팅 -> 시작점임
     */
    fun setSelectedPlant(plant: String) {
        _uiState.value = _uiState.value?.copy(selectedPlant = plant)
        // 이름 세팅과 동시에 차트 데이터도 세팅
        getPlantEntryList(plant)
        // 가격 차이도 세팅
        getPlantPriceGap(plant)
    }
    /**
     * 차트 데이터 데이터 레이어에서 뽑아오기
     */
    fun getPlantEntryList(plant: String){
        _uiState.value = _uiState.value?.copy(plantEntryList = homeDomain.getPlantEntryList(plant))
    }
    /**
     * 가격 차이
     */
    fun getPlantPriceGap(plant: String){
        val gapList = homeDomain.getPlantPriceGap()
        for (i in gapList.indices){
            if (gapList.get(i).plantName == plant){
                val priceGap = gapList.get(i).gap
                val priceGapString: String
                if ( priceGap < 0){
                    priceGapString = priceGap.absoluteValue.toString() + "원 하락 ▼"
                }
                else if (priceGap > 0){
                    priceGapString = priceGap.toString() + "원 상승 ▲"
                }
                else{
                    priceGapString = "가격 변동 없음"
                }
                gapList.get(i).gap.toString()
                _uiState.value = _uiState.value?.copy(priceGapString = priceGapString)
            }
        }
    }
    /**
     * 현재 가격 받아오기
     */
    private fun setCurrentPrice():List<CurrentPriceModel>{
        return homeDomain.getCurrentPriceList()
    }
}
/**
 * 홈 화면 프래그먼트 UI 상태 홀딩
 */
data class HomeFragmentStatus(
    val selectedPlant: String,
    val recyclerViewItemList: List<PlantDataModel>,
    val plantEntryList: List<Entry>,
    val currentPriceList: List<CurrentPriceModel>,
    val priceGapString: String
)