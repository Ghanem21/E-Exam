package com.example.e_exam.network.studentSubject

import com.example.e_exam.network.levelsAndDepartments.Department
import com.example.e_exam.network.levelsAndDepartments.Level
import com.squareup.moshi.Json

data class Subject(
    val id : Int,
    val name : String,
    @Json(name = "level_id") val levelId : String,
    @Json(name = "dept_id") val deptId : String,
    @Json(name = "prof_id") val profId : String,
    @Json(name = "levels") val level : Level,
    @Json(name = "departments") val department: Department,
    @Json(name = "professors") val professor: Professor,



    )
