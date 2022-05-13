package com.example.e_exam.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_exam.R
import com.example.e_exam.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.*


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private var binding: FragmentSplashScreenBinding? = null
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen,container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.splashScreen = this
        job = Job()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        job.cancel()
    }

    fun navigate() {
        val coroutineScope = CoroutineScope(Dispatchers.Main + job)
        coroutineScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_splashScreenFragment_to_logInFragment)
        }
    }
}