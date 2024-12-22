package com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Domain

import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Data.PlantDataRepository
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.CurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.PlantDataModel
import com.github.mikephil.charting.data.Entry

class HomeDomain {
    private val plantDataRepository = PlantDataRepository()

    fun getPlantDataList(): List<PlantDataModel> {
        return plantDataRepository.getPlantDataList()
    }
    fun getPlantEntryList(plant: String): List<Entry> {
        return plantDataRepository.getPlantEntryList(plant)
    }
    /**
     * 현재 작물 가격 리스트
     */
    fun getCurrentPriceList(): List<CurrentPriceModel> {
        return plantDataRepository.getCurrentPriceList()
    }
}