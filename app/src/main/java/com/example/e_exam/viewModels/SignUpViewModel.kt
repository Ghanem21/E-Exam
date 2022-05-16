package com.example.e_exam.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.levelsAndDepartments.Department
import com.example.e_exam.network.levelsAndDepartments.Level
import com.example.e_exam.network.signUp.RegisterRespond
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.util.*

class SignUpViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _levelId = MutableLiveData<Int>()
    val levelId: LiveData<Int> = _levelId

    private val _deptId = MutableLiveData<Int>()
    val deptId: LiveData<Int> = _deptId

    private val _levels = MutableLiveData<List<Level>>()
    val levels: LiveData<List<Level>> = _levels

    private val _departments = MutableLiveData<List<Department>>()
    val departments: LiveData<List<Department>> = _departments

    private val _registerRespond = MutableLiveData<RegisterRespond>()
    val registerRespond: LiveData<RegisterRespond> = _registerRespond
    init{
        getDepartmentAndLevel()
    }
    fun doSignUp(username: String,email: String,password: String,confirmPassword: String,levelId: Int,deptId: Int): Deferred<Any> {
        _username.value = username
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _levelId.value = levelId
        _deptId.value = deptId

        return viewModelScope.async {
            try {
                val registerRespond = ExamApi.retrofitService.createAccount(email,password,confirmPassword,levelId,deptId)
                _registerRespond.value = registerRespond
                return@async true
            }catch (ex:Exception){
                Log.d("TAG", "doLogIn:" + ex.message)
            }
            return@async false
        }
    }

    private fun getDepartmentAndLevel(): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                val levelAndDepartmentRespond =
                    ExamApi.retrofitService.getLevelsAndDepartment(Locale.getDefault().language)
                _levels.value = levelAndDepartmentRespond.levels
                _departments.value = levelAndDepartmentRespond.departments
            }catch (ex:Exception){
                Log.d("TAG", "doLogIn:" + ex.message)
            }
            return@async true
        }
    }
}