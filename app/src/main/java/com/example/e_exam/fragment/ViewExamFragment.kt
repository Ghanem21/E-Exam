package com.example.e_exam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.e_exam.R
import com.example.e_exam.adapter.QuestionAdapter
import com.example.e_exam.databinding.FragmentViewExamBinding
import com.example.e_exam.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewExamFragment : Fragment() {

    private lateinit var binding: FragmentViewExamBinding
    private lateinit var parentJob: Job
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var answers: List<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_exam, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentJob = Job()
        answers = listOf()
        binding.apply {
            sharedViewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            viewExamFragment = this@ViewExamFragment
        }
        CoroutineScope(Dispatchers.Main + parentJob).launch {
            if (sharedViewModel.getExamQuestion().await())
                binding.recyclerview.adapter = QuestionAdapter(
                    sharedViewModel.questions.value!!,
                    sharedViewModel.token.value!!
                )
            val adapter:QuestionAdapter = binding.recyclerview.adapter as QuestionAdapter
            answers = adapter.getAnswer()
        }
    }

    fun submit() {
        sharedViewModel.submitAnswers(answers)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentJob.cancel()
    }
}