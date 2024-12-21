package com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Data

import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.PlantDataModel
import com.braveberry.govtech2024_applepricepredict.R
import com.github.mikephil.charting.data.Entry

/**
 * 작물 데이터 레포
 */
class PlantDataRepository {
    fun getPlantDataList(): List<PlantDataModel> {
        return buildPlantDataList()
    }
    fun getPlantEntryList(plant: String): List<Entry> {
        return buildPlantEntryList(plant)
    }
    /**
     * 작물별 가격 데이터 생성, 파라미터로 작물 이름 받음
     */
    private fun buildPlantEntryList(plant: String): List<Entry> {
        val entryList = mutableListOf<Entry>()
        entryList.add(Entry(0f, 3000f))
        entryList.add(Entry(1f, 5000f))
        entryList.add(Entry(2f, 4000f))
        return entryList
    }
    /**
     * 여기에 추가할 작물 넣으면 됨
     */
    private fun buildPlantDataList(): List<PlantDataModel> {
        val appleData = PlantDataModel("사과", "5000원", R.drawable.apple_image)
        val potatoData = PlantDataModel("감자", "4000원", R.drawable.potato_image)
        val pepperData = PlantDataModel("고추", "3000원", R.drawable.pepper_image)

        return listOf(appleData, potatoData, pepperData)
    }
}