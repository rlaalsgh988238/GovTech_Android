package com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.braveberry.govtech2024_applepricepredict.PageHome.DataLayer.Model.AnalysisPlantDataModel
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.databinding.CropItemViewBinding

class HorizonRecyclerAdapter(val lifecycleOwner: LifecycleOwner, val homeViewModel: HomeViewModel) : RecyclerView.Adapter<HorizonRecyclerAdapter.ViewHolder>() {
    var items = homeViewModel.uiState.value!!.recyclerViewItemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CropItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class ViewHolder(val lifecycleOwner: LifecycleOwner, val viewModel: HomeViewModel, private val binding: CropItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
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
            // 아이템 선택 시, 아이템 확대
            viewModel.uiState.observe(lifecycleOwner, Observer { state -> // lifecycleOwner 사용
                if (state.selectedPlant == plant){
                    // 동적으로 width와 height 변경
                    val layoutParams = binding.itemLayout.layoutParams

                    // DP 값을 픽셀로 변환
                    val newWidthInDp = 150 // 원하는 width (DP 단위)
                    val newHeightInDp = 150 // 원하는 height (DP 단위)

                    val context = binding.itemLayout.context
                    layoutParams.width = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        newWidthInDp.toFloat(),
                        context.resources.displayMetrics
                    ).toInt() // DP를 픽셀로 변환하여 설정

                    layoutParams.height = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        newHeightInDp.toFloat(),
                        context.resources.displayMetrics
                    ).toInt() // DP를 픽셀로 변환하여 설정
                }
                else{
                    // 동적으로 width와 height 변경
                    val layoutParams = binding.itemLayout.layoutParams

                    // DP 값을 픽셀로 변환
                    val newWidthInDp = 120 // 원하는 width (DP 단위)
                    val newHeightInDp = 120 // 원하는 height (DP 단위)

                    val context = binding.itemLayout.context
                    layoutParams.width = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        newWidthInDp.toFloat(),
                        context.resources.displayMetrics
                    ).toInt() // DP를 픽셀로 변환하여 설정

                    layoutParams.height = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        newHeightInDp.toFloat(),
                        context.resources.displayMetrics
                    ).toInt() // DP를 픽셀로 변환하여 설정
                }
            })
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(newItems: List<AnalysisPlantDataModel>){
        items = newItems
        notifyDataSetChanged()
    }
}

