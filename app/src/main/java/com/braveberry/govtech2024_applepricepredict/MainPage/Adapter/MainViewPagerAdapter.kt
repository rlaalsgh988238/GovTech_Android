package com.braveberry.govtech2024_applepricepredict.MainPage.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.braveberry.govtech2024_applepricepredict.PageAnalysis.AnalysisFragment
import com.braveberry.govtech2024_applepricepredict.PageHome.UILayer.HomeFragment
import com.braveberry.govtech2024_applepricepredict.PageProfile.ProfileFragment

class MainViewPagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val items = listOf("homeFragment", "analysisFragment", "profileFragment")

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> AnalysisFragment()
            2 -> ProfileFragment()
            else -> {
                HomeFragment()
            }
        }
    }
}