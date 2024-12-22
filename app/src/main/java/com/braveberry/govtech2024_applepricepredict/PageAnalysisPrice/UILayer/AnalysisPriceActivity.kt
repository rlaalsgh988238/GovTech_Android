package com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer.ViewModel.AnalysisPriceViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.databinding.ActivityAnalysisPriceBinding

class AnalysisPriceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAnalysisPriceBinding
    private lateinit var viewModel: AnalysisPriceViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnalysisPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 뷰 모델 초기화
        viewModel = ViewModelProvider(this).get(AnalysisPriceViewModel::class.java)
        // 옵저버 세팅
        viewModel.uiState.observe(this, Observer { state ->
            binding.plantName.text = state.selectedPlant
            binding.plantImage.setImageResource(state.imagePath)
            binding.currentPrice.text = state.currentPrice.toString() + "원"
            binding.priceGapText.text = state.priceGapString
        })

        // 뷰 모델에 데이터 넣기
        val selectedPlant = intent.getStringExtra("PlantName")
        if (selectedPlant != null) {
            viewModel.getPlantName(selectedPlant)
        }
        else{
            viewModel.getPlantName("사과")
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}