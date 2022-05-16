package com.example.e_exam.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.logIn.LogInRespond
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class LogInViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _logInRespond = MutableLiveData<LogInRespond>()
    val logInRespond: LiveData<LogInRespond> = _logInRespond

    fun doLogIn(email: String, password: String): Deferred<Any> {
        return viewModelScope.async {
            _email.value = email
            _password.value = password

            try {
                _logInRespond.value = ExamApi.retrofitService.logIn(email, password)
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "doLogIn:" + ex.message)
            }
            return@async false
        }
    }

}