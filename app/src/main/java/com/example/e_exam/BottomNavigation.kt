package com.example.e_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.e_exam.adapter.ViewPagerAdapter
import com.example.e_exam.databinding.ActivityBottomNavigationBinding
import com.example.e_exam.fragment.AboutUsFragment
import com.example.e_exam.fragment.HomeFragment
import com.example.e_exam.fragment.MyAccountFragment
import kotlinx.coroutines.*

class BottomNavigation : AppCompatActivity() {
    private lateinit var binding:ActivityBottomNavigationBinding
    private lateinit var parentJob: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = listOf(
            MyAccountFragment(),HomeFragment(),AboutUsFragment()
        )
        parentJob = Job()
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch{
            delay(8)
            launch {
                binding.viewPager.adapter = ViewPagerAdapter(fragments, this@BottomNavigation)
                binding.viewPager.currentItem = 1
                binding.bottomNavigation.menu.getItem(1).isChecked = true
            }
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.aboutUsFragment -> {
                    binding.viewPager.currentItem = 2
                }
                R.id.homeFragment -> {
                    binding.viewPager.currentItem = 1
                }
                R.id.myAccountFragment -> {
                    binding.viewPager.currentItem = 0
                }
            }
            return@setOnItemSelectedListener true
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }

    override fun onBackPressed() {
        if(binding.viewPager.currentItem == 1){
            super.onBackPressed()
        }else if(binding.viewPager.currentItem == 0){
            binding.viewPager.currentItem += 1
        } else{
            binding.viewPager.currentItem -= 1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }
}