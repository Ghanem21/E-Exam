package com.example.e_exam.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_exam.R
import com.example.e_exam.databinding.FragmentLogInBinding
import com.example.e_exam.viewModels.LogInViewModel
import kotlinx.coroutines.*

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var parentJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            logInFragment = this@LogInFragment
        }
        parentJob = Job()
    }

    fun onLogIn() {
        lateinit var email: String
        lateinit var password: String
        binding.apply {
            logInButton.isEnabled = false
            email = emailEditText.text.toString()
            password = passwordEditText.text.toString()
        }




        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            launch {
                binding.progressBar.visibility = View.VISIBLE
            }
            val end = viewModel.doLogIn(email, password)
            if (end == true) {
                Toast.makeText(context, viewModel.logInRespond.value!!.msg, Toast.LENGTH_SHORT)
                    .show()

                val errNum = viewModel.logInRespond.value!!.errNum
                val msg = viewModel.logInRespond.value!!.msg
                binding.apply {

                    if (errNum == "S000")
                    // navigate to Home Fragment
                    else if (errNum == "E007")
                        emailEditText.error = msg
                    else if (errNum == "E1001" || errNum == "E002")
                        passwordEditText.error = msg

                }
            }
            binding.logInButton.isEnabled = true
            binding.progressBar.visibility = View.GONE
        }
    }

    fun onSignUp() {
        findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentJob.cancel()
    }
}