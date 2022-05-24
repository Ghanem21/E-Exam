package com.example.e_exam.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.e_exam.BottomNavigation
import com.example.e_exam.R
import com.example.e_exam.databinding.FragmentGradeViewBinding
import com.example.e_exam.viewModels.ExamViewModel


class GradeViewFragment : Fragment() {
    private val viewModel: ExamViewModel by activityViewModels()
    private lateinit var binding: FragmentGradeViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_grade_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            gradeFragment = this@GradeViewFragment
            examViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    fun returnHome() {
        startActivity(Intent(requireContext(), BottomNavigation::class.java))
        requireActivity().finish()
    }
}