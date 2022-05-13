package com.example.e_exam.network.logIn

import com.squareup.moshi.Json

data class LogInRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "Student") val student: Student?
)
