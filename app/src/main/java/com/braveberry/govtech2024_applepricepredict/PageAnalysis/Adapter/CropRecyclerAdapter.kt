package com.braveberry.govtech2024_applepricepredict.PageAnalysis.Adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.ViewModel.AnalysisViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter.HorizonRecyclerAdapter
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter.HorizonRecyclerAdapter.ViewHolder
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.databinding.CropItemAnalysisBinding
import com.braveberry.govtech2024_applepricepredict.databinding.CropItemViewBinding

class CropRecyclerAdapter(val lifecycleOwner: LifecycleOwner, val homeViewModel: AnalysisViewModel): RecyclerView.Adapter<CropRecyclerAdapter.ViewHolder>() {
//todo 수정하기 items
    var items = homeViewModel.uiState.value!!.recyclerViewItemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CropItemAnalysisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(lifecycleOwner, homeViewModel, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(items[position].itemImagePath)
        holder.price.text = holder.currentPrice(items[position].itemName).toString() + "원"
        holder.itemName.text = items[position].itemName
        holder.setListener(items[position].itemName)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val lifecycleOwner: LifecycleOwner, val viewModel: AnalysisViewModel, private val binding: CropItemAnalysisBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.itemImage
        val itemName = binding.itemName
        val price = binding.priceText
        /**
         * 아이템 클릭 시 로직
         */
        fun setListener(plant: String){
            // 아이템 선택 시, 뷰모델에 작물 이름 세팅
            binding.itemLayout.setOnClickListener {
                viewModel.setSelectedPlant(plant)
            }
            //그냥 연동 넘기게만 하면 될 듯 ?


        }
        /**
         * 현재 1kg 당 가격
         */
        fun currentPrice(plant: String): Int{
            val currentPriceList = viewModel.uiState.value!!.currentPriceList
            var currentPrice = 0
            for (i in 0 until currentPriceList.size){
                if (plant == currentPriceList.get(i).plantName){
                    currentPrice = currentPriceList.get(i).currentPrice
                }
            }
            return currentPrice
        }
    }

}