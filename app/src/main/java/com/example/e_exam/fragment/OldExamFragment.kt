package com.example.e_exam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_exam.R
import com.example.e_exam.adapter.OldAnswerAdapter
import com.example.e_exam.adapter.OldExamAdapter
import com.example.e_exam.databinding.FragmentOldExamBinding
import com.example.e_exam.network.viewOldAnswers.Answer

class OldExamFragment : Fragment() {
    private lateinit var binding: FragmentOldExamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_old_exam, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.adapter = OldAnswerAdapter(OldExamAdapter.answers)
    }
}