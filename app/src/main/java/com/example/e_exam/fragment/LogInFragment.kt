package com.example.e_exam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_exam.R
import com.example.e_exam.databinding.FragmentLogInBinding
import com.example.e_exam.viewModels.LogInViewModel

class LogInFragment : Fragment() {
    private var binding: FragmentLogInBinding? = null
    private val viewModel: LogInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            logInFragment = this@LogInFragment
        }
    }

    fun onLogIn() {
        val email = binding?.emailEditText?.text.toString()
        val password = binding?.passwordEditText?.text.toString()
        viewModel.doLogIn(email, password)
    }
    fun onSignUp(){
        findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
    }
}