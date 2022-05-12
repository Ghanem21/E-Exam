package com.example.e_exam.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.network.ExamApi
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun doLogIn(email: String, password: String) {
        viewModelScope.launch {
            _email.value = email
            _password.value = password
            val logInRespond = ExamApi.retrofitService.logIn(email, password)
            if (logInRespond.status) {
                Log.d("TAG", "doLogIn: " + logInRespond.msg)
            }
        }
    }

}