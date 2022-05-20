package com.example.e_exam.network.checkExistExam

import com.squareup.moshi.Json

data class CheckExistExamRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "Exam Info") val exams: List<Exam>?
)
