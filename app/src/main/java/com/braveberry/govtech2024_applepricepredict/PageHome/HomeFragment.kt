package com.braveberry.govtech2024_applepricepredict.PageHome

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.braveberry.govtech2024_applepricepredict.R
import com.braveberry.govtech2024_applepricepredict.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class HomeFragment : Fragment() {
    private lateinit var lineChart: LineChart
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart = binding.lineChart
        setupChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupChart() {
        // 데이터 생성
        val entries = ArrayList<Entry>()
        entries.add(Entry(3f, 100f))  // 3월
        entries.add(Entry(6f, 180f))  // 6월
        entries.add(Entry(9f, 150f))  // 9월
        entries.add(Entry(12f, 186f)) // 12월

        val dataSet = LineDataSet(entries, "가격 변동 추이")
        dataSet.apply {
            color = Color.rgb(67, 115, 78)  // 차트 선 색상
            lineWidth = 2f
            setDrawCircles(false)  // 데이터 포인트 원 숨기기
            mode = LineDataSet.Mode.CUBIC_BEZIER  // 부드러운 곡선
            setDrawFilled(false)  // 선 아래 채우기 없음
        }

        val lineData = LineData(dataSet)

        // 차트 스타일링
        lineChart.apply {
            data = lineData
            description.isEnabled = false  // 설명 텍스트 숨기기
            legend.isEnabled = false      // 범례 숨기기

            // X축 설정
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 3f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "${value.toInt()}월"
                    }
                }
            }
            // Y축 설정
            axisLeft.apply {
                setDrawGridLines(true)
                setDrawLabels(true)
            }
            axisRight.isEnabled = false  // 오른쪽 Y축 비활성화
            // 차트 여백 설정
            setExtraOffsets(20f, 20f, 20f, 20f)

            // 애니메이션
            animateX(1000)
        }
    }
}
