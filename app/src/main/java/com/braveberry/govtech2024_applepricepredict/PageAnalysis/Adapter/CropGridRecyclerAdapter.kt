package com.braveberry.govtech2024_applepricepredict.PageAnalysis.Adapter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.AnalysisDetailFragment
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.ViewModel.AnalysisViewModel
import com.braveberry.govtech2024_applepricepredict.R
import com.braveberry.govtech2024_applepricepredict.databinding.CropGridItemViewBinding
import com.braveberry.govtech2024_applepricepredict.databinding.CropItemAnalysisBinding

class CropGridRecyclerAdapter(
    val lifecycleOwner: LifecycleOwner,
    val homeViewModel: AnalysisViewModel
) : RecyclerView.Adapter<CropGridRecyclerAdapter.ViewHolder>() {

    var items = homeViewModel.uiState.value!!.recyclerViewItemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CropGridItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(lifecycleOwner, homeViewModel, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        // 이미지, 가격, 이름 등 설정
        holder.image.setImageResource(currentItem.itemImagePath)
        holder.price.text = holder.currentPrice(currentItem.itemName).toString() + "원"
        holder.itemName.text = currentItem.itemName
        if (currentItem.itemName == "고추") {
            // 고추일 경우 가격 하락 텍스트와 카드 색상을 파란색으로 설정
            holder.priceText.text = "전년 대비 가격 하락"

            val blueColor = ContextCompat.getColor(holder.itemView.context, R.color.blue_color)
            holder.cardPrice.setCardBackgroundColor(blueColor)

        }else if(currentItem.itemName == "샤인머스캣"){
            holder.priceText.text = "출하시기 임박"

            val gray = ContextCompat.getColor(holder.itemView.context, R.color.gray)
            holder.cardPrice.setCardBackgroundColor(gray)

        }
        else {
            // 다른 품목은 ViewModel의 가격 텍스트와 색상을 사용
            holder.priceText.text = homeViewModel.uiState.value?.priceText
            val cardColor = homeViewModel.uiState.value?.cardColor
            holder.cardPrice.setCardBackgroundColor(Color.parseColor(cardColor))
        }
        holder.setListener(currentItem.itemName)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        val lifecycleOwner: LifecycleOwner,
        val viewModel: AnalysisViewModel,
        private val binding: CropGridItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val image = binding.itemImage
        val itemName = binding.itemName
        val price = binding.priceText
        val cardPrice = binding.cvPrice
        val priceText = binding.tvPrice



        fun setListener(plant: String) {
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

        fun currentPrice(plant: String): Int {
            val currentPriceList = viewModel.uiState.value!!.currentPriceList
            var currentPrice = 0
            for (i in 0 until currentPriceList.size) {
                if (plant == currentPriceList[i].plantName) {
                    currentPrice = currentPriceList[i].currentPrice
                }
            }
            return currentPrice
        }
    }
}
