package com.braveberry.govtech2024_applepricepredict.PageAnalysis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.Adapter.CropGridRecyclerAdapter
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.Adapter.CropRecyclerAdapter
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.ViewModel.AnalysisViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter.HorizonRecyclerAdapter
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.R
import com.braveberry.govtech2024_applepricepredict.databinding.FragmentAnalysisBinding
import com.braveberry.govtech2024_applepricepredict.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart


class AnalysisFragment : Fragment() {
    private lateinit var lineChart: LineChart
    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnalysisViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnalysisViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 리사이클러뷰 세팅
        val adapter = CropRecyclerAdapter(lifecycleOwner = this, viewModel)
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        //gridRecyclerView 쓰기
        val gridAdapter = CropGridRecyclerAdapter(lifecycleOwner = this, viewModel)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2) // 2 열로 설정

        binding.rvCropList.layoutManager = gridLayoutManager // 변수명 수정
        binding.rvCropList.adapter = gridAdapter // 변수명 수정

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}