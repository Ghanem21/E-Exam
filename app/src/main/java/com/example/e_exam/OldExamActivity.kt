package com.example.e_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment

class OldExamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_exam)

        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val backArrow = findViewById<ImageView>(R.id.back)
        backArrow.setOnClickListener {
            finish()
        }
    }
}