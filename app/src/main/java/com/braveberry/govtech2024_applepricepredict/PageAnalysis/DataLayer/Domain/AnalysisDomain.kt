package com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Domain

import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Data.AnalysisPlantDataRepository
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisCurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisPlantDataModel
import com.github.mikephil.charting.data.Entry

class AnalysisDomain {
    private val plantDataRepository = AnalysisPlantDataRepository()

    fun getPlantDataList(): List<AnalysisPlantDataModel> {
        return plantDataRepository.getPlantDataList()
    }
    fun getPlantEntryList(plant: String): List<Entry> {
        return plantDataRepository.getPlantEntryList(plant)
    }
    /**
     * 현재 작물 가격 리스트
     */
    fun getCurrentPriceList(): List<AnalysisCurrentPriceModel> {
        return plantDataRepository.getCurrentPriceList()
    }
}