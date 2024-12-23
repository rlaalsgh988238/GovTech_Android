package com.braveberry.govtech2024_applepricepredict.PageAnalysis

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.ViewModel.AnalysisViewModel
import com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer.AnalysisPriceActivity
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.R
import com.braveberry.govtech2024_applepricepredict.databinding.FragmentAnalysisDetailBinding
import com.braveberry.govtech2024_applepricepredict.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter


class AnalysisDetailFragment : Fragment() {
    private lateinit var lineChart: LineChart
    private var _binding: FragmentAnalysisDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnalysisViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AnalysisViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnalysisDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = binding.lineChart
        viewModel.setSelectedPlant("반입량")

        setupChart(viewModel.uiState.value!!.plantEntryList)

    }

    private fun setupChart(entryList: List<Entry>) {
        val dataSet = LineDataSet(entryList, "가격 변동 추이")
        // 데이터 셋 적용
        dataSet.apply {
            color = Color.rgb(67, 115, 78)  // 차트 선 색상
            lineWidth = 2f
            setDrawCircles(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER  // 부드러운 곡선
            setDrawFilled(false)  // 선 아래 채우기 없음
        }
        // 선 그래프 세팅
        val lineData = LineData(dataSet)
        // 차트 스타일링
        lineChart.apply {
            data = lineData
            // 설명 텍스트 숨기기
            description.isEnabled = false
            // 범례 숨기기
            legend.isEnabled = false
            // X축 설정

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 3f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        if(value.toInt()==0){
                            return "12월"
                        }else{

                            return "${value.toInt()}월"
                        }
                    }
                }
            }

            // Y축 설정
            axisLeft.apply {
                setDrawGridLines(true)
                setDrawLabels(true)
            }
            axisRight.isEnabled = false
            // 차트 여백 설정
            setExtraOffsets(20f, 20f, 20f, 20f)
            // 애니메이션
            animateX(700)
        }
    }
    private fun setPriceGap(priceGapString: String){
        binding.priceGapText.text = priceGapString
    }
}