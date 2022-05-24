package com.example.e_exam.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.network.ExamApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ExamViewModel: ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _questionsId = MutableLiveData<List<Int>>()
    val questionsId:LiveData<List<Int>> = _questionsId

    private val _examId = MutableLiveData<Int>()
    val examId : LiveData<Int> = _examId

    private val _scored = MutableLiveData<Int>()
    val scored : LiveData<Int> = _scored

    private val _total = MutableLiveData<Int>()
    val total : LiveData<Int> = _total

    private val _grade = MutableLiveData<String>()
    val grade:LiveData<String> = _grade


    fun setToken(token : String){
        _token.value = token
    }

    fun setQuestionsId(questionsId : List<Int>){
        _questionsId.value = questionsId
    }

    fun submitAnswers(answers : List<String>): Deferred<String> {
        return viewModelScope.async {
            val submitExamRespond = ExamApi.retrofitService.submitExam(token.value!!,
                examId.value!!,answers)
            _total.value = submitExamRespond.grade!!.total
            _scored.value = submitExamRespond.grade.scored
            return@async "${submitExamRespond.grade!!.scored} / + ${submitExamRespond.grade.total}"
        }
    }

    fun setGrade(grade:String){
        _grade.value = grade
    }
}