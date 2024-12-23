package com.braveberry.govtech2024_applepricepredict.PageAnalysis.Adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.AnalysisDetailFragment
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.ViewModel.AnalysisViewModel
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter.HorizonRecyclerAdapter
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.Adapter.HorizonRecyclerAdapter.ViewHolder
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.ViewModel.HomeViewModel
import com.braveberry.govtech2024_applepricepredict.R
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

                val context = binding.root.context
                if (context is AppCompatActivity) {
                    val activity = context
                    val transaction = activity.supportFragmentManager.beginTransaction()

                    // 새로운 Fragment를 add
                    val fragment = AnalysisDetailFragment()

                    // Fragment가 이미 추가되어 있지 않으면 추가
                    val existingFragment =
                        activity.supportFragmentManager.findFragmentByTag(AnalysisDetailFragment::class.java.simpleName)

                    if (existingFragment == null) {
                        transaction.add(
                            R.id.fragment_analysis,
                            fragment,
                            AnalysisDetailFragment::class.java.simpleName
                        )
                        transaction.addToBackStack(null)  // 뒤로 가기 스택에 추가
                        transaction.commit()
                    }
                }
            }


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