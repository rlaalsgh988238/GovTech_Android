package com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.braveberry.govtech2024_applepricepredict.databinding.ActivityAnalysisPriceBinding

class AnalysisPriceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAnalysisPriceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnalysisPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}