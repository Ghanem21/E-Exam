package com.example.e_exam.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.e_exam.MainActivity
import com.example.e_exam.R
import com.example.e_exam.adapter.SubjectAdapter
import com.example.e_exam.databinding.FragmentHomeBinding
import com.example.e_exam.viewModels.SharedViewModel
import kotlinx.coroutines.*


class HomeFragment : Fragment() {

    private lateinit var parentJob: Job
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter:SubjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentJob = Job()
        binding.apply {
            viewModel = sharedViewModel
            homeFragment = this@HomeFragment
            lifecycleOwner = viewLifecycleOwner
        }
        val sharedPreferences =
            requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        sharedViewModel.setToken(sharedPreferences.getString("token", "")!!)

        sharedViewModel.setRefresh(true)
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            if (sharedViewModel.getStudentSubject().await()) {
                adapter = SubjectAdapter(sharedViewModel.subjects, sharedViewModel.token.value!!)
                binding.recyclerview.adapter = adapter
            }else{
                startActivity(Intent(requireContext(),MainActivity::class.java))
                requireActivity().finish()
            }
            sharedViewModel.setRefresh(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentJob.cancel()
    }

    fun onRefresh(){
        sharedViewModel.setRefresh(true)
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            sharedViewModel.getStudentSubject().await()
            adapter = SubjectAdapter(sharedViewModel.subjects, sharedViewModel.token.value!!)
            binding.recyclerview.adapter = adapter
            sharedViewModel.setRefresh(false)
        }
    }
}