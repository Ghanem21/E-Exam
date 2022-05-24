package com.example.e_exam.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.logIn.LogInRespond
import kotlinx.coroutines.async

class LogInViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()

    private val _password = MutableLiveData<String>()

    private val _logInRespond = MutableLiveData<LogInRespond>()
    val logInRespond: LiveData<LogInRespond> = _logInRespond

    suspend fun doLogIn(email: String, password: String): Any {
        return viewModelScope.async {
            _email.value = email
            _password.value = password

            try {
                _logInRespond.value = ExamApi.retrofitService.logIn(email, password)
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "doLogIn:" + ex.message)
                return@async false
            }
        }.await()
    }

    fun saveDate(context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("studentId" , logInRespond.value!!.student!!.id)
        editor.putString("studentName" , logInRespond.value!!.student!!.name)
        editor.putString("studentEmail" , logInRespond.value!!.student!!.email)
        editor.putString("token" , logInRespond.value!!.student!!.token)
        editor.apply()
    }
}