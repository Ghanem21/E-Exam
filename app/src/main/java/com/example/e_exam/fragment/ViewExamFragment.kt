package com.example.e_exam.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.e_exam.R
import com.example.e_exam.adapter.QuestionAdapter
import com.example.e_exam.databinding.FragmentViewExamBinding
import com.example.e_exam.viewModels.ExamViewModel
import kotlinx.coroutines.*

class ViewExamFragment : Fragment() {

    private lateinit var binding: FragmentViewExamBinding
    private lateinit var parentJob: Job
    private lateinit var answers: List<String>
    private val viewModel: ExamViewModel by activityViewModels()
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

        val bundle = requireActivity().intent.extras

        val token = bundle!!.getString("token","")
        viewModel.setToken(token)

        val list = bundle.getIntegerArrayList("question")!!.toArray().toList() as List<Int>
        viewModel.setQuestionsId(list)

        val examId = bundle.getInt("examId")
        viewModel.setExamId(examId)

        val endExamTime = bundle.getLong("endExamTime")
        viewModel.setExamEndTime(endExamTime)

        adapter = QuestionAdapter(viewModel.questionsId.value!!,viewModel.token.value!!)
        binding.recyclerview.adapter = adapter

        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            val currentTime = System.currentTimeMillis()
            if(viewModel.endExamTime.value!! > currentTime)
            delay(viewModel.endExamTime.value!! - currentTime)
            submit()
        }

    }

    fun submit() {
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            viewModel.submitAnswers(adapter.getAnswer())
            findNavController().navigate(R.id.action_viewExamFragment2_to_gradeViewFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentJob.cancel()
    }
}