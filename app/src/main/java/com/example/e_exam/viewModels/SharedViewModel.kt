package com.example.e_exam.viewModels

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

    private val _currentExamId = MutableLiveData<Int>()
    val currentExamId: LiveData<Int> = _currentExamId

    private val _currentSubjectId = MutableLiveData<Int>()
    val currentSubjectId: LiveData<Int> = _currentSubjectId

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    init {
        _questions.value = listOf()
        _token.value =
            "bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvZS1leGFtLmFobWVkLXByb2plY3RzLm1lXC9hcGlcL3N0dWRlbnRcL0xvZ2luIiwiaWF0IjoxNjUzMzcwMzExLCJleHAiOjE2NTMzODgzMTEsIm5iZiI6MTY1MzM3MDMxMSwianRpIjoiYlhsZml3cHBtVVI2YUJuZSIsInN1YiI6MywicHJ2IjoiOTUyNDk0NDE2MDFlNDdlZjI0ZGY2MmNjZWE4YTVkZDFmODNmNGZlMiJ9.fC7LC1-LDdQffxkA_Qa6D7Y92fzMBHWeTzrYWODEPXY"
        _lang.value = Locale.getDefault().language
    }

    fun getStudentSubject(): Deferred<List<Subject>?> {
        return viewModelScope.async {
            try {
                val getStudentSubjectRespond = ExamApi.retrofitService.getStudentSubject(
                    lang.value.toString(),
                    token.value.toString()
                )
                return@async getStudentSubjectRespond.subject
            } catch (ex: Exception) {
                Log.d("TAG", "getStudentSubject: " + ex.message)
                return@async null
            }
        }
    }

    fun getExamQuestion(): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                val examQuestionRespond =
                    ExamApi.retrofitService.getExamQuestion(7, 3, token.value!!)
                if (examQuestionRespond.status)
                    _questions.value = examQuestionRespond.questions!!
                else
                    Log.d("TAG", "getExamQuestion: " + examQuestionRespond.msg)
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "getStudentSubject: " + ex.message)
                _questions.value = listOf()
                return@async false
            }
        }
    }

    fun SetToken(token: String) {
        _token.value = "bearer " + token
    }

    fun submitAnswers(answers : List<String>){
        viewModelScope.launch {
            val submitExamRespond = ExamApi.retrofitService.submitExam(token.value!!,7,answers)
            Log.d("TAG", "submitAnswers: " + submitExamRespond.grade!!.scored + "/" + submitExamRespond.grade.total)
        }
    }
}