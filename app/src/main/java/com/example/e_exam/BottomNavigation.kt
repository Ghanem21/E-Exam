package com.example.e_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.account_fragment -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.home_fragment -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.about_us_fragment -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
    }
}