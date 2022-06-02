package com.example.e_exam.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.e_exam.R
import com.example.e_exam.adapter.OldExamAdapter
import com.example.e_exam.databinding.FragmentMyAccountBinding
import com.example.e_exam.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MyAccountFragment : Fragment() {
    private lateinit var parentJob: Job
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentMyAccountBinding
    private lateinit var adapter: OldExamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentJob = Job()
        binding.apply {
            viewModel = sharedViewModel
            accountFragment = this@MyAccountFragment
            lifecycleOwner = viewLifecycleOwner
        }
        binding.oldExamsLayout.viewModel = sharedViewModel
        binding.personalData.accountFragment = this@MyAccountFragment
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(
            "PREFERENCE_NAME",
            Context.MODE_PRIVATE
        )
        binding.personalData.name.text = sharedPreferences.getString("studentName", "")
        binding.personalData.email.text = sharedPreferences.getString("studentEmail", "")

        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            sharedViewModel.setRefresh(true)
            if (sharedViewModel.oldExams.value == null)
                sharedViewModel.getOldExamRespond().await()
            adapter = OldExamAdapter(sharedViewModel.oldExams.value ?: listOf())
            binding.oldExamsLayout.recyclerview.adapter = adapter
            sharedViewModel.setRefresh(false)

        }

    }

    fun doLogOut() {
        sharedViewModel.doLogOut(requireActivity())
    }

    fun onRefresh() {
        sharedViewModel.setRefresh(true)
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            if (sharedViewModel.oldExams.value == null)
                sharedViewModel.getOldExamRespond().await()
            adapter = OldExamAdapter(sharedViewModel.oldExams.value ?: listOf())
            binding.oldExamsLayout.recyclerview.adapter = adapter
            sharedViewModel.setRefresh(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }
}