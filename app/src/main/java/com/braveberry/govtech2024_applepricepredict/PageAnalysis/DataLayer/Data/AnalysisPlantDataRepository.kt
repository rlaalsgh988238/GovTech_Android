package com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Data

import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisCurrentPriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisDatePriceModel
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisPlantDataModel
import com.braveberry.govtech2024_applepricepredict.R
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 작물 데이터 레포
 */
class AnalysisPlantDataRepository {
    fun getPlantDataList(): List<AnalysisPlantDataModel> {
        return buildPlantDataList()
    }
    fun getPlantEntryList(plant: String): List<Entry> {
        return buildPlantEntryList(plant)
    }
    /**
     * 현재 작물 가격 리스트 받기
     */
    fun getCurrentPriceList(): List<AnalysisCurrentPriceModel> {
        val currentPriceList = mutableListOf<AnalysisCurrentPriceModel>()

        // Gson 객체 생성
        val gson = Gson()
        val listType = object : TypeToken<List<AnalysisDatePriceModel>>() {}.type

        // 사과 데이터 파싱
        val appleJsonData = AnalysisDatePriceData().appleData
        val appleDatePriceList: List<AnalysisDatePriceModel> = gson.fromJson(appleJsonData, listType)
        // 사과 데이터의 마지막 값 가져오기
        val lastApplePrediction = appleDatePriceList.last().prediction
        // 사과 가격 업데이트
        currentPriceList.add(AnalysisCurrentPriceModel("사과", lastApplePrediction))

        // 감자 데이터 파싱
        val potatoJsonData = AnalysisDatePriceData().potatoData
        val potatoDatePriceList: List<AnalysisDatePriceModel> = gson.fromJson(potatoJsonData, listType)
        // 감자 데이터의 마지막 값 가져오기
        val lastPotatoPrediction = potatoDatePriceList.last().prediction
        // 감자 가격 업데이트
        currentPriceList.add(AnalysisCurrentPriceModel("감자", lastPotatoPrediction))

        // 고추 데이터 파싱
        val pepperJsonData = AnalysisDatePriceData().pepperData
        val pepperDatePriceList: List<AnalysisDatePriceModel> = gson.fromJson(pepperJsonData, listType)
        // 고추 데이터의 마지막 값 가져오기
        val lastPepperPrediction = pepperDatePriceList.last().prediction
        // 고추 가격 업데이트
        currentPriceList.add(AnalysisCurrentPriceModel("고추", lastPepperPrediction))

        val shineMuscatJsonData = AnalysisDatePriceData().shineMuscatData
        val shineMuscatDatePriceList: List<AnalysisDatePriceModel> = gson.fromJson(shineMuscatJsonData, listType)
        // 샤머 데이터의 마지막 값 가져오기
        val lastShineMuscatPrediction = shineMuscatDatePriceList.last().prediction
        // 샤머 가격 업데이트
        currentPriceList.add(AnalysisCurrentPriceModel("샤인머스캣", lastShineMuscatPrediction))

        return currentPriceList
    }
    /**
     * 작물별 가격 데이터 생성, 파라미터로 작물 이름 받음
     */
    private fun buildPlantEntryList(plant: String): List<Entry> {
        val gson = Gson()
        val listType = object : TypeToken<List<AnalysisDatePriceModel>>() {}.type
        val datePriceList: List<AnalysisDatePriceModel>
        when(plant){
            "사과" -> datePriceList = gson.fromJson(AnalysisDatePriceData().appleData, listType)
            "감자" -> datePriceList = gson.fromJson(AnalysisDatePriceData().potatoData, listType)
            "고추" -> datePriceList = gson.fromJson(AnalysisDatePriceData().pepperData, listType)
            "샤인머스캣"->datePriceList = gson.fromJson(AnalysisDatePriceData().shineMuscatData, listType)
            else -> datePriceList = gson.fromJson(AnalysisDatePriceData().appleData, listType)
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
    private fun buildPlantDataList(): List<AnalysisPlantDataModel> {
        val appleData = AnalysisPlantDataModel("사과", "5000원", R.drawable.apple_image)
        val potatoData = AnalysisPlantDataModel("감자", "4000원", R.drawable.potato_image)
        val pepperData = AnalysisPlantDataModel("고추", "3000원", R.drawable.pepper_image)
        val shineMuscatData = AnalysisPlantDataModel("샤인머스캣", "30000원", R.drawable.shine_muscat)
        return listOf(appleData, potatoData, pepperData,shineMuscatData)
    }
}