package com.example.e_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.e_exam.databinding.ActivityExamBinding
import com.example.e_exam.viewModels.ExamViewModel
import com.example.e_exam.viewModels.SharedViewModel

class ExamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExamBinding
    private lateinit var navController: NavController
    private lateinit var examViewModel: ExamViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        binding.apply {
            examViewModel = examViewModel
            lifecycleOwner = lifecycleOwner
        }
        val bundle = intent.extras
        examViewModel.setToken(bundle!!.getString("token",""))
        val list = bundle.getIntegerArrayList("question")!!.toArray().toList() as List<Int>
        examViewModel.setQuestionsId(list)



    }
}