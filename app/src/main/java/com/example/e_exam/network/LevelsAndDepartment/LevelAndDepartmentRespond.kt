package com.example.e_exam.network.LevelsAndDepartment

import com.squareup.moshi.Json

data class LevelAndDepartmentRespond(
   val status: Boolean,
   val errNum: String,
   val msg: String,
   @Json(name = "The levels") val levels : List<Level>,
   @Json(name = "The departments") val departments: List<Department>
)
