package com.braveberry.govtech2024_applepricepredict.MainPage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.braveberry.govtech2024_applepricepredict.MainPage.Adapter.MainViewPagerAdapter
import com.braveberry.govtech2024_applepricepredict.MainPage.ViewModel.MainViewModel
import com.braveberry.govtech2024_applepricepredict.R
import com.braveberry.govtech2024_applepricepredict.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.mainViewPager
        viewPager.isUserInputEnabled = false
        val adapter = MainViewPagerAdapter(this)
        viewPager.adapter = adapter

        viewModel.uiState.observe(this, Observer { state ->
            when(state.navigationStatus){
                "homeFragment" -> {
                    viewPager.setCurrentItem(0, true)
                }
                "analysisFragment" -> {
                    viewPager.setCurrentItem(1, true)
                }
                "profileFragment" -> {
                    viewPager.setCurrentItem(2, true)
                }
            }
        })

        setNavigationListener()
    }

    fun setNavigationListener(){
        binding.mainNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_home -> {
                    viewModel.setNavigationStatus("homeFragment")
                    true
                }
                R.id.fragment_search -> {
                    viewModel.setNavigationStatus("analysisFragment")
                    true
                }
                R.id.fragment_profile -> {
                    viewModel.setNavigationStatus("profileFragment")
                    true
                }
                else -> false
            }
        }
    }
}
