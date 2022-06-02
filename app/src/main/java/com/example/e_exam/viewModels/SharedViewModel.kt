package com.example.e_exam.viewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.MainActivity
import com.example.e_exam.R
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.studentSubject.Subject
import com.example.e_exam.network.viewExam.Question
import com.example.e_exam.network.viewOldExams.Exams
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.util.*

enum class ExamApiStatus { LOADING, ERROR, DONE }

class SharedViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ExamApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ExamApiStatus> = _status

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _lang = MutableLiveData<String>()
    val lang: LiveData<String> = _lang

    private val _questions = MutableLiveData<List<Question>>()

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean> = _refresh

    private val _oldExams = MutableLiveData<List<Exams>>()
    val oldExams: LiveData<List<Exams>> = _oldExams

    init {
        _token.value = ""
        _lang.value = Locale.getDefault().language
    }

    fun getStudentSubject(): Deferred<Boolean> {
        return viewModelScope.async {
            _status.value = ExamApiStatus.LOADING
            try {
                val getStudentSubjectRespond = ExamApi.retrofitService.getStudentSubject(
                    lang.value.toString(),
                    token.value.toString()
                )
                if (getStudentSubjectRespond.status)
                    _subjects.value = getStudentSubjectRespond.subject!!
                else {
                    Log.d("TAG", "getStudentSubject: " + getStudentSubjectRespond.msg)
                    return@async false
                }
                _status.value = ExamApiStatus.DONE
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "getStudentSubject: " + ex.message)
                if(subjects.value == null)
                    _status.value = ExamApiStatus.ERROR
                return@async false
            }
        }
    }

    fun doLogOut(activity: Activity) {
        Log.d("TAG", "doLogOut: ")
        viewModelScope.launch {
            MaterialAlertDialogBuilder(activity)
                    .setTitle("Are you sure \uD83E\uDD7A?")
                    .setMessage("you will need log in next time")
                    .setCancelable(false)
                    .setIcon(R.drawable.logo)
                    .setPositiveButton("Yes") { _, _ ->
                        launch {
                            val logOutRespond = ExamApi.retrofitService.logOut(token.value!!)
                            Log.d("TAG", "doLogOut: " + logOutRespond.msg)
                        }
                       activity.getSharedPreferences(
                                    "PREFERENCE_NAME",
                                    Context.MODE_PRIVATE
                                ).edit().clear().apply()
                                activity.startActivity(Intent(activity, MainActivity::class.java))
                                activity.finish()
                            }.setNegativeButton("No") { _, _ ->
                    }
                    .show()
        }
    }

    fun setToken(token: String) {
        _token.value = "bearer $token"
    }

    fun setRefresh(boolean: Boolean) {
        _refresh.value = boolean
    }

    fun getOldExamRespond(): Deferred<Boolean>{
        return viewModelScope.async {
            _status.value = ExamApiStatus.LOADING
            try {
                val getOldExamRespond =
                    ExamApi.retrofitService.getOldExam(token.value!!)
                _oldExams.value = getOldExamRespond.exams!!
                Log.d("TAG", "getOldExamRespond: " + getOldExamRespond.status)
                _status.value = ExamApiStatus.DONE
                return@async true

            }catch (ex :Exception){
                Log.d("TAG", "onViewCreated: " + ex.message)
                if(oldExams.value == null)
                    _status.value = ExamApiStatus.ERROR
                return@async false
            }
        }
    }
}