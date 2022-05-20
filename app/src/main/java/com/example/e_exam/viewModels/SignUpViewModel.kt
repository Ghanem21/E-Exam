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
import kotlinx.coroutines.async
import java.util.*

class SignUpViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()

    private val _email = MutableLiveData<String>()

    private val _password = MutableLiveData<String>()

    private val _confirmPassword = MutableLiveData<String>()

    private val _levelId = MutableLiveData<Int>()
    private val levelId: LiveData<Int> = _levelId

    private val _deptId = MutableLiveData<Int>()
    private val deptId: LiveData<Int> = _deptId

    private val _levels = MutableLiveData<List<Level>>()
    val levels: LiveData<List<Level>> = _levels

    private val _departments = MutableLiveData<List<Department>>()
    val departments: LiveData<List<Department>> = _departments

    private val _registerRespond = MutableLiveData<RegisterRespond>()
    val registerRespond: LiveData<RegisterRespond> = _registerRespond

    private val _dataStatus = MutableLiveData(false)
    val dataStatus : LiveData<Boolean> = _dataStatus

    suspend fun doSignUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Any {
        _username.value = username
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        val levelId = levelId.value ?: 0
        val deptId = deptId.value ?: 0
        return viewModelScope.async {
            try {
                val registerRespond =
                    ExamApi.retrofitService.createAccount(
                        username ,
                        email,
                        password,
                        confirmPassword,
                        levelId,
                        deptId
                    )

                _registerRespond.value = registerRespond

                Log.d("TAG", "doSignUp:" + registerRespond.msg)
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "doSignUp:" + ex.message)
                return@async false
            }

        }.await()
    }

    suspend fun getDepartmentAndLevel(): Boolean {
        return viewModelScope.async {
            try {
                val levelAndDepartmentRespond =
                    ExamApi.retrofitService.getLevelsAndDepartment(Locale.getDefault().language)
                _levels.value = levelAndDepartmentRespond.levels
                _departments.value = levelAndDepartmentRespond.departments
                Log.d("TAG", "getDepartmentAndLevel:" + levelAndDepartmentRespond.msg)
                return@async true
            } catch (ex: Exception) {
                Log.d("TAG", "getDepartmentAndLevel:" + ex.message)
                return@async false
            }
        }.await()
    }

    fun setDepartment(department: Department){
        _deptId.value = department.id
    }

    fun setLevel(level: Level){
        _levelId.value = level.id
    }

    fun setDataStatus(boolean: Boolean){
        _dataStatus.value = boolean
    }
}