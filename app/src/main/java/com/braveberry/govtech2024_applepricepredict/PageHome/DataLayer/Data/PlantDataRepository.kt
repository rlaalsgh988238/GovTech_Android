package com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Data

import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.CurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.DatePriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.PlantDataModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.PlantPriceGapModel
import com.braveberry.govtech2024_applepricepredict.R
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 작물 데이터 레포
 */
class PlantDataRepository {
    fun getPlantPriceGap(): List<PlantPriceGapModel> {
        val appleDatePriceList = parsePlantData(DatePriceData().appleData)
        val lastApplePrediction = appleDatePriceList.last().prediction
        val secondLastApplePrediction = appleDatePriceList[appleDatePriceList.size - 2].prediction
        //사과 가격 차이
        val applePriceGap = lastApplePrediction - secondLastApplePrediction

        val potatoDatePriceList = parsePlantData(DatePriceData().potatoData)
        val lastPotatoPrediction = potatoDatePriceList.last().prediction
        val secondLastPotatoPrediction = potatoDatePriceList[potatoDatePriceList.size - 2].prediction
        //감자 가격 차이
        val potatoPriceGap = lastPotatoPrediction - secondLastPotatoPrediction


        val pepperDatePriceList = parsePlantData(DatePriceData().pepperData)
        val lastPepperPrediction = pepperDatePriceList.last().prediction
        val secondLastPepperPrediction = pepperDatePriceList[pepperDatePriceList.size - 2].prediction
        //고추 가격 차이
        val pepperPriceGap = lastPepperPrediction - secondLastPepperPrediction

        return listOf(
            PlantPriceGapModel("사과", applePriceGap),
            PlantPriceGapModel("감자", potatoPriceGap),
            PlantPriceGapModel("고추", pepperPriceGap)
        )
    }
    fun getPlantDataList(): List<PlantDataModel> {
        return buildPlantDataList()
    }
    fun getPlantEntryList(plant: String): List<Entry> {
        return buildPlantEntryList(plant)
    }
    /**
     * 현재 작물 가격 리스트 받기
     */
    fun getCurrentPriceList(): List<CurrentPriceModel> {
        val currentPriceList = mutableListOf<CurrentPriceModel>()

        val appleDatePriceList = parsePlantData(DatePriceData().appleData)
        // 사과 데이터의 마지막 값 가져오기
        val lastApplePrediction = appleDatePriceList.last().prediction
        // 사과 가격 업데이트
        currentPriceList.add(CurrentPriceModel("사과", lastApplePrediction))

        val potatoDatePriceList = parsePlantData(DatePriceData().potatoData)
        // 감자 데이터의 마지막 값 가져오기
        val lastPotatoPrediction = potatoDatePriceList.last().prediction
        // 감자 가격 업데이트
        currentPriceList.add(CurrentPriceModel("감자", lastPotatoPrediction))

        val pepperDatePriceList = parsePlantData(DatePriceData().pepperData)
        // 고추 데이터의 마지막 값 가져오기
        val lastPepperPrediction = pepperDatePriceList.last().prediction
        // 고추 가격 업데이트
        currentPriceList.add(CurrentPriceModel("고추", lastPepperPrediction))

        return currentPriceList
    }
    /**
     * 작물별 가격 데이터 생성, 파라미터로 작물 이름 받음
     */
    private fun buildPlantEntryList(plant: String): List<Entry> {
        val gson = Gson()
        val listType = object : TypeToken<List<DatePriceModel>>() {}.type
        val datePriceList: List<DatePriceModel>
        when(plant){
            "사과" -> datePriceList = gson.fromJson(DatePriceData().appleData, listType)
            "감자" -> datePriceList = gson.fromJson(DatePriceData().potatoData, listType)
            "고추" -> datePriceList = gson.fromJson(DatePriceData().pepperData, listType)
            else -> datePriceList = gson.fromJson(DatePriceData().appleData, listType)
        }
        val entryList = mutableListOf<Entry>()
        for (i in datePriceList.indices) {
            entryList.add(Entry(i.toFloat(), datePriceList[i].prediction.toFloat()))
        }
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
    /**
     * Json에서 읽고 파싱
     */
    private fun parsePlantData(plantData: String): List<DatePriceModel>{
        val gson = Gson()
        val listType = object : TypeToken<List<DatePriceModel>>() {}.type
        val datePriceList: List<DatePriceModel> = gson.fromJson(plantData, listType)
        return datePriceList
    }
}