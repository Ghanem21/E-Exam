package com.example.e_exam.viewModels

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.R
import com.example.e_exam.network.ExamApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ExamViewModel : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _questionsId = MutableLiveData<List<Int>>()
    val questionsId: LiveData<List<Int>> = _questionsId

    private val _examId = MutableLiveData<Int>()
    private val examId: LiveData<Int> = _examId

    private val _scored = MutableLiveData<Int>()

    private val _total = MutableLiveData<Int>()

    private val _endExamTime = MutableLiveData<Long>()
    val endExamTime: LiveData<Long> = _endExamTime

    private val _grade = MutableLiveData<String>()
    val grade: LiveData<String> = _grade


    fun setToken(token: String) {
        _token.value = token
    }

    fun setQuestionsId(questionsId: List<Int>) {
        _questionsId.value = questionsId
    }

    fun submitAnswers(answers: List<String>, context: Context): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.Main) {
            val dialog = ProgressDialog(context)
            dialog.setTitle("Waiting....")
            dialog.setCancelable(false)
            dialog.setIcon(R.drawable.logo)
            dialog.setMessage("Getting data please wait\uD83D\uDE0A")
            dialog.show()
            try {
                val submitExamRespond = ExamApi.retrofitService.submitExam(
                    token.value!!,
                    examId.value!!, answers
                )
                Log.d("TAG", "submitAnswers: " + submitExamRespond.grade!!.total)
                _total.value = submitExamRespond.grade.total
                _scored.value = submitExamRespond.grade.scored
                _grade.value =
                    "${submitExamRespond.grade.scored} / ${submitExamRespond.grade.total}"
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "submitAnswers: " + ex.message)
            }
            dialog.hide()
            return@async false
        }
    }

    fun setExamId(id: Int) {
        _examId.value = id
    }

    fun setExamEndTime(long: Long) {
        _endExamTime.value = long
    }
}