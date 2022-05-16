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
import com.example.e_exam.databinding.FragmentSignUpBinding
import com.example.e_exam.viewModels.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    val viewModel: SignUpViewModel by viewModels()
    private lateinit var parentJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            signUpFragment = this@SignUpFragment
        }
        parentJob = Job()
    }
    fun onSignUp(){
        lateinit var username: String
        lateinit var email: String
        lateinit var password: String
        lateinit var confirmPassword: String
        var levelId: Int
        var deptId: Int

        binding.apply {
            username = usernameEditText.text.toString()
            email = emailEditText.text.toString()
            password = passwordEditText.text.toString()
            confirmPassword = passwordEditText.text.toString()
            levelId = levelSpinner.id
            deptId = deptSpinner.id
            signUpButton.isEnabled = false
        }

        val end = viewModel.doSignUp(username,email,password,confirmPassword,levelId,deptId)
        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {

            launch {
                binding.progressBar.visibility = View.VISIBLE
            }

            if (end.await() == true) {
                Toast.makeText(context, viewModel.registerRespond.value!!.msg, Toast.LENGTH_SHORT)
                    .show()


                binding.apply {

                    if (viewModel.registerRespond.value!!.errNum == "S000")
                    // navigate to Home Fragment
                    else if (viewModel.registerRespond.value!!.errNum == "E007")
                        emailEditText.error = viewModel.registerRespond.value!!.msg
                    else if (viewModel.registerRespond.value!!.errNum == "E1001" || viewModel.registerRespond.value!!.errNum == "E002")
                        passwordEditText.error = viewModel.registerRespond.value!!.msg

                }
            }
            binding.signUpButton.isEnabled = true
            binding.progressBar.visibility = View.GONE
        }
    }

    fun onLogIn(){
        findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }

}