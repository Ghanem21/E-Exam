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
    private var binding: FragmentLogInBinding? = null
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var parentJob: Job

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
        parentJob = Job()
    }

    fun onLogIn() {
        lateinit var email: String
        lateinit var password: String
        binding?.apply {
            logInButton.isEnabled = false
            email = emailEditText.text.toString()
            password = passwordEditText.text.toString()
        }


        val end = viewModel.doLogIn(email, password)

        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {

            launch {
                binding!!.progressBar.visibility = View.VISIBLE
            }

            if (end.await() == true) {
                binding!!.progressBar.visibility = View.GONE
                Toast.makeText(context, viewModel.logInRespond.value!!.msg, Toast.LENGTH_SHORT)
                    .show()
            }

            binding?.apply {

                if (viewModel.logInRespond.value!!.errNum == "S000")
                // navigate to Home Fragment
                else if (viewModel.logInRespond.value!!.errNum == "E007")
                    viewModel.logInRespond.value!!.msg.also {
                        emailEditText.error = it
                    }
                else if (viewModel.logInRespond.value!!.errNum == "E1001" || viewModel.logInRespond.value!!.errNum == "E002")
                    viewModel.logInRespond.value!!.msg.also {
                        passwordEditText.error = it
                    }
                logInButton.isEnabled = true
            }
        }

    }

    fun onSignUp() {
        findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        parentJob.cancel()
    }
}