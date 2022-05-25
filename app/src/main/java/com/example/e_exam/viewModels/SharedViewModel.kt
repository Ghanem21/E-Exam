package com.example.e_exam.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.studentSubject.Subject
import com.example.e_exam.network.viewExam.Question
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class SharedViewModel : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _lang = MutableLiveData<String>()
    val lang: LiveData<String> = _lang

    private val _questions = MutableLiveData<List<Question>>()

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean> = _refresh

    init {
        _questions.value = listOf()
        _subjects.value = listOf()
        _token.value = ""
        _lang.value = Locale.getDefault().language
    }

    fun getStudentSubject(): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                val getStudentSubjectRespond = ExamApi.retrofitService.getStudentSubject(
                    lang.value.toString(),
                    token.value.toString()
                )
                if (getStudentSubjectRespond.status)
                    _subjects.value = getStudentSubjectRespond.subject!!
                else
                    Log.d("TAG", "getStudentSubject: " + getStudentSubjectRespond.msg)
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "getStudentSubject: " + ex.message)
                return@async false
            }
        }
    }
    fun doLogOut(context :Context){
        try {
            viewModelScope.launch {
                val logOutRespond = ExamApi.retrofitService.logOut(token.value!!)
                if (logOutRespond.status) {
                    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
                        "PREFERENCE_NAME",
                        Context.MODE_PRIVATE
                    )
                    sharedPreferences.edit().clear().apply()
                }
            }
        }catch(ex:Exception){
            Log.d("TAG", "doLogOut: "+  ex.message)
        }
    }

    fun setToken(token: String) {
        _token.value = "bearer $token"
    }

    fun setRefresh(boolean: Boolean){
        _refresh.value = boolean
    }
}