package com.braveberry.govtech2024_applepricepredict.PageHome.UILayer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.braveberry.govtech2024_applepricepredict.PageAnalysisPrice.UILayer.AnalysisPriceActivity
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter.HorizonRecyclerAdapter
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * 홈 화면 프래그먼트
 */
class HomeFragment : Fragment() {
    private lateinit var lineChart: LineChart
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰모델 초기화
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 리사이클러뷰 세팅
        val adapter = HorizonRecyclerAdapter(lifecycleOwner = this, viewModel)
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        // 뷰모델 변화 관찰
        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            Log.d("test log", "HomeFragment: $state")
            adapter.updateItem(state.recyclerViewItemList)
            setupChart(state.plantEntryList)
            setPriceGap(state.priceGapString)
        })
        //차트 초기 세팅
        lineChart = binding.lineChart
        setupChart(viewModel.uiState.value!!.plantEntryList)

        binding.priceAnalysisLayout.setOnClickListener {
            // 변동 요인 분석
            val intent = Intent(requireContext(), AnalysisPriceActivity::class.java)
            // 현재 선택된 작물 이름을 인텐트에 추가
            intent.putExtra("PlantName", viewModel.uiState.value!!.selectedPlant)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /**
     * 차트 세팅, 파라미터로 entryList를 받아 차트를 세팅한다.
     */
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
                        return "${value.toInt()}일"
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
