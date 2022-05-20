package com.example.e_exam.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_exam.R
import com.example.e_exam.bindDepartment
import com.example.e_exam.bindLevel
import com.example.e_exam.databinding.FragmentSignUpBinding
import com.example.e_exam.viewModels.SignUpViewModel
import kotlinx.coroutines.*


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
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
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        parentJob = Job()


        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            val end = viewModel.getDepartmentAndLevel()
            if (end || viewModel.dataStatus.value == true) {
                viewModel.setDataStatus(true)
                bindDepartment(binding.departmentsAutoComplete, viewModel.departments)
                bindLevel(binding.levelsAutoCompleteTv, viewModel.levels)
                binding.departmentsAutoComplete.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        val department = viewModel.departments.value!![position]
                        viewModel.setDepartment(department)
                        Log.d("TAG", "onItemSelected: $department")
                    }
            }

            binding.levelsAutoCompleteTv.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val level = viewModel.levels.value!![position]
                    viewModel.setLevel(level)
                    Log.d("TAG", "onItemSelected: $level")
                }

        }
    }

    fun onSignUp() {
        lateinit var username: String
        lateinit var email: String
        lateinit var password: String
        lateinit var confirmPassword: String

        binding.apply {
            username = usernameEditText.text.toString()
            email = emailEditText.text.toString()
            password = passwordEditText.text.toString()
            confirmPassword = confirmPasswordEditText.text.toString()
            signUpButton.isEnabled = false
        }

        val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
        coroutineScope.launch {
            launch {
                binding.progressBar.visibility = View.VISIBLE
            }
            val end = viewModel.doSignUp(username, email, password, confirmPassword)

            if (end == true) {
                Toast.makeText(
                    context,
                    viewModel.registerRespond.value!!.msg,
                    Toast.LENGTH_SHORT
                )
                    .show()

                val errNum = viewModel.registerRespond.value!!.errNum
                val msg = viewModel.registerRespond.value!!.msg
                binding.apply {

                    if (errNum == "S000")
                    // navigate to Home Fragment
                    else if (errNum == "E007")
                        emailEditText.error = msg
                    else if (errNum == "E1001" || errNum == "E002")
                        passwordEditText.error = msg

                }
            }
            binding.signUpButton.isEnabled = true
            binding.progressBar.visibility = View.GONE
        }
    }

    fun onLogIn() {
        findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }

}