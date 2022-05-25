package com.example.e_exam.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.e_exam.MainActivity
import com.example.e_exam.R
import com.example.e_exam.databinding.FragmentHomeBinding
import com.example.e_exam.databinding.FragmentMyAccountBinding
import com.example.e_exam.viewModels.SharedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MyAccountFragment : Fragment() {
    private lateinit var parentJob: Job
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentMyAccountBinding
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
            myAccountFragment = this@MyAccountFragment
            lifecycleOwner = viewLifecycleOwner
        }
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("PREFERENCE_NAME",
            Context.MODE_PRIVATE)
        binding.name.text = sharedPreferences.getString("studentName","")
        binding.email.text = sharedPreferences.getString("studentEmail","")

    }

    fun doLogOut() {
        sharedViewModel.doLogOut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}