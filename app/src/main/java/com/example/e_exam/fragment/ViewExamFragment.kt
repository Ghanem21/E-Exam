package com.example.e_exam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_exam.R
import com.example.e_exam.adapter.QuestionAdapter
import com.example.e_exam.databinding.FragmentViewExamBinding
import com.example.e_exam.viewModels.ExamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewExamFragment : Fragment() {

    private lateinit var binding: FragmentViewExamBinding
    private lateinit var parentJob: Job
    private lateinit var answers: List<String>
    private lateinit var viewModel: ExamViewModel
    lateinit var token: String
    private lateinit var adapter:QuestionAdapter

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
                viewExamFragment = this@ViewExamFragment
            }
        adapter = QuestionAdapter(viewModel.questionsId.value!!,viewModel.token.value!!)
        binding.recyclerview.adapter = adapter
    }

    fun submit() {
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            viewModel.setGrade(viewModel.submitAnswers(adapter.getAnswer()).await())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentJob.cancel()
    }
}